package io.github.wendergalan.desafio.api.resource;

import io.github.wendergalan.desafio.api.dto.LicitacaoDTO;
import io.github.wendergalan.desafio.api.dto.PropostaDTO;
import io.github.wendergalan.desafio.api.dto.ResponseDTO;
import io.github.wendergalan.desafio.model.entity.Licitacao;
import io.github.wendergalan.desafio.model.entity.Proposta;
import io.github.wendergalan.desafio.service.LicitacaoService;
import io.github.wendergalan.desafio.service.PropostaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static io.github.wendergalan.desafio.Util.criarListaDeErros;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * The type Licitacao controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/licitacoes")
@Api("Proposta Controller")
public class LicitacaoController {

    private final ModelMapper modelMapper;
    private final LicitacaoService licitacaoService;
    private final PropostaService propostaService;

    /**
     * Salvar response entity.
     *
     * @param licitacaoDTO the licitacao dto
     * @param result       the result
     * @return the response entity
     */
    @PostMapping
    @ApiOperation(value = "Cria uma licitação")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    public ResponseEntity salvar(@Valid @RequestBody LicitacaoDTO licitacaoDTO, BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(criarListaDeErros(result.getAllErrors()));

        Licitacao entity = licitacaoService.salvar(modelMapper.map(licitacaoDTO, Licitacao.class));

        return ResponseEntity
                .created(MvcUriComponentsBuilder.fromController(getClass()).build().toUri())
                .body(modelMapper.map(entity, LicitacaoDTO.class));
    }

    /**
     * Alterar response entity.
     *
     * @param id           the id
     * @param licitacaoDTO the licitacao dto
     * @param result       the result
     * @return the response entity
     */
    @PutMapping("/{id}")
    @ApiOperation("Altera uma licitação.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    public ResponseEntity alterar(@PathVariable Long id,
                                  @Valid @RequestBody LicitacaoDTO licitacaoDTO, BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(criarListaDeErros(result.getAllErrors()));

        return licitacaoService.buscarPorId(id)
                .map(licitacao -> {
                    licitacao.setDescricao(licitacaoDTO.getDescricao());
                    licitacao.setTipoClassificacao(licitacaoDTO.getTipoClassificacao());
                    licitacaoService.salvar(licitacao);
                    return ResponseEntity.ok().body(modelMapper.map(licitacao, LicitacaoDTO.class));
                })
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    /**
     * Deletar por id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    @ApiOperation("Deleta a licitação pelo ID.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    public ResponseEntity deletarPorId(@PathVariable Long id) {
        Licitacao licitacao = licitacaoService
                .buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        licitacaoService.deletarPorId(licitacao);
        return ResponseEntity.noContent().build();
    }

    /**
     * Buscar por id response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    @ApiOperation("Busca a proposta por ID.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    public ResponseEntity buscarPorId(@PathVariable Long id) {
        LicitacaoDTO licitacaoDTO = licitacaoService
                .buscarPorId(id)
                .map(licitacao -> modelMapper.map(licitacao, LicitacaoDTO.class))
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        return ResponseEntity.ok(licitacaoDTO);
    }

    /**
     * Buscar todas licitacaos response entity.
     *
     * @param page the page
     * @param size the size
     * @return the response entity
     */
    @GetMapping
    @ApiOperation(value = "Busca todas as licitações.")
    @ApiResponses(@ApiResponse(code = 200, message = "Success", response = ResponseDTO.class))
    public ResponseEntity buscarTodasLicitacaos(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        Page<Licitacao> result = licitacaoService.buscarTodasLicitacaos(page, size);
        return ResponseEntity.ok(new ResponseDTO<>(
                result.isEmpty() ? 0 : page,
                result.getTotalPages(),
                result.getTotalElements(),
                result.getContent().stream().map(licitacao -> modelMapper.map(licitacao, LicitacaoDTO.class)).collect(Collectors.toList())));
    }

    /**
     * Buscar propostas da licitacao response entity.
     *
     * @param id   the id
     * @param page the page
     * @param size the size
     * @return the response entity
     */
    @GetMapping("/{id}/propostas")
    @ApiOperation(value = "Busca todas as propostas de uma licitação.")
    @ApiResponses(@ApiResponse(code = 200, message = "Success", response = ResponseDTO.class))
    public ResponseEntity buscarPropostasDaLicitacao(@PathVariable Long id,
                                                     @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                                     @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        Licitacao licitacao = licitacaoService.buscarPorId(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        Page<Proposta> result = propostaService.buscarPropostasPorLicitacao(licitacao, PageRequest.of(page - 1, size == -1 ? 10000000 : size));
        return ResponseEntity.ok(new ResponseDTO<>(
                result.isEmpty() ? 0 : page,
                result.getTotalPages(),
                result.getTotalElements(),
                result.getContent().stream().map(proposta -> modelMapper.map(proposta, PropostaDTO.class)).collect(Collectors.toList())
        ));
    }

    /**
     * Classificar propostas da licitacao response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @PutMapping("/{id}/classificar-propostas")
    @ApiOperation(value = "Classifica todas as propostas da licitação")
    @ApiResponses(@ApiResponse(code = 200, message = "Success"))
    public ResponseEntity classificarPropostasDaLicitacao(@PathVariable Long id) {
        Licitacao licitacao = licitacaoService.buscarPorId(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        propostaService.classificarPropostasPorLicitacao(licitacao);
        return ResponseEntity.noContent().build();
    }
}
