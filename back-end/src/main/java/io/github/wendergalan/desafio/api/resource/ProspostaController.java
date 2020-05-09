package io.github.wendergalan.desafio.api.resource;

import io.github.wendergalan.desafio.api.dto.PropostaDTO;
import io.github.wendergalan.desafio.api.dto.ResponseDTO;
import io.github.wendergalan.desafio.model.entity.Licitacao;
import io.github.wendergalan.desafio.model.entity.Proposta;
import io.github.wendergalan.desafio.service.PropostaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
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
 * The type Prosposta controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/propostas")
@Api("Proposta Controller")
public class ProspostaController {

    private final ModelMapper modelMapper;
    private final PropostaService propostaService;

    /**
     * Salvar response entity.
     *
     * @param propostaDTO the proposta dto
     * @param result      the result
     * @return the response entity
     */
    @PostMapping
    @ApiOperation(value = "Cria uma proposta")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    public ResponseEntity salvar(@Valid @RequestBody PropostaDTO propostaDTO, BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(criarListaDeErros(result.getAllErrors()));

        Proposta entity = modelMapper.map(propostaDTO, Proposta.class);
        entity.setLicitacao(modelMapper.map(propostaDTO.getLicitacao(), Licitacao.class));
        entity = propostaService.salvar(entity);

        return ResponseEntity
                .created(MvcUriComponentsBuilder.fromController(getClass()).build().toUri())
                .body(modelMapper.map(entity, PropostaDTO.class));
    }

    /**
     * Alterar response entity.
     *
     * @param id          the id
     * @param propostaDTO the proposta dto
     * @param result      the result
     * @return the response entity
     */
    @PutMapping("/{id}")
    @ApiOperation("Altera uma proposta.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    public ResponseEntity alterar(@PathVariable Long id,
                                  @Valid @RequestBody PropostaDTO propostaDTO, BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(criarListaDeErros(result.getAllErrors()));

        return propostaService.buscarPorId(id)
                .map(proposta -> {
                    proposta.setFornecedor(propostaDTO.getFornecedor());
                    proposta.setNota(propostaDTO.getNota());
                    proposta.setPreco(propostaDTO.getPreco());
                    propostaService.salvar(proposta);
                    return ResponseEntity.ok().body(modelMapper.map(proposta, PropostaDTO.class));
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
    @ApiOperation("Deleta a proposta pelo ID.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    public ResponseEntity deletarPorId(@PathVariable Long id) {
        Proposta proposta = propostaService
                .buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        propostaService.deletarPorId(proposta);
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
        PropostaDTO propostaDTO = propostaService
                .buscarPorId(id)
                .map(proposta -> modelMapper.map(proposta, PropostaDTO.class))
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        return ResponseEntity.ok(propostaDTO);
    }

    /**
     * Buscar todas propostas response entity.
     *
     * @param page the page
     * @param size the size
     * @return the response entity
     */
    @GetMapping
    @ApiOperation(value = "Busca todas as propostas.")
    @ApiResponses(@ApiResponse(code = 200, message = "Success", response = ResponseDTO.class))
    public ResponseEntity buscarTodasPropostas(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                               @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        Page<Proposta> allOfPage = propostaService.buscarTodasPropostas(page, size);
        return ResponseEntity.ok(new ResponseDTO<>(
                allOfPage.isEmpty() ? 0 : page,
                allOfPage.getTotalPages(),
                allOfPage.getTotalElements(),
                allOfPage.getContent().stream().map(proposta -> modelMapper.map(proposta, PropostaDTO.class)).collect(Collectors.toList())));
    }
}
