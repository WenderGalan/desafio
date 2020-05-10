<template src="./PropostaTemplate.html" />

<script>
import Form from '../../app/Mixins/Form'

export default {
  name: 'Proposta',
  mixins: [Form],
  data: () => ({
    loadingData: false,
    calculateWidths: true,
    dialog: false,
    headers: [
      {
        text: 'Fornecedor',
        align: 'start',
        value: 'fornecedor',
        sortable: false
      },
      {
        text: 'Nota',
        value: 'nota',
        sortable: false
      },
      {
        text: 'Preço',
        value: 'preco',
        sortable: false
      },
      {
        text: 'Data do Cadastro',
        value: 'dataCadastro',
        sortable: false
      },
      {
        text: 'Ações',
        value: 'actions',
        align: 'center',
        widths: '15%',
        sortable: false
      }
    ],
    formHasErrors: false,
    licitacao: null,
    record: {
      fornecedor: null,
      nota: null,
      preco: null,
      dataCadastro: null
    },
    defaultRecord: {
      fornecedor: '',
      nota: null,
      preco: null,
      dataCadastro: null
    },
    serverItemsLength: 0,
    service: {
      resource: '/propostas',
      serviceLicitacao: '/licitacoes'
    }
  }),
  /**
   */
  computed: {
    /**
     * Carrega os headers com base no parametro da licitação.
     */
    computedHeaders () {
      if (this.licitacao.tipoClassificacao !== 'NOTA_PRECO') {
        return this.headers.filter((item) => item.value !== 'nota')
      }
      return this.headers
    },
    formTitle () {
      return this.editedIndex === -1 ? 'Incluir Proposta' : 'Alterar Proposta'
    },
    form () {
      return {
        fornecedor: this.record.fornecedor,
        preco: this.record.preco
      }
    }
  },
  /**
   */
  methods: {
    /**
     */
    classificarPropostas () {
      this.$http.put(`${this.service.serviceLicitacao}/${this.licitacao.id}/classificar-propostas`)
        .then(() => {
          this.updatePagination({ page: this.optionsTable.page, size: this.optionsTable.itemsPerPage })
        })
    },
    /**
     */
    getColor (item) {
      if (item >= 6) {
        return 'green'
      }
      return 'red'
    },
    /**
     */
    findItems ({ page, size }) {
      this.loadingData = true
      this.$http.get(`${this.service.serviceLicitacao}/${this.licitacao.id}/propostas`, { params: { page, size } })
        .then((response) => {
          this.loadingData = false
          this.serverItemsLength = response.data.totalElements
          this.items = response.data.content
        })
        .finally(() => (this.loadingData = false))
    },
    /**
     */
    beforeSave () {
      // Atribui a licitacao antes de salvar
      this.record.licitacao = this.licitacao
    }
  },
  created () {
    // Atribui o objeto da licitacao
    this.defaultRecord.licitacao = this.$route.params.licitacao
    this.licitacao = this.$route.params.licitacao
    this.updatePagination({ page: this.optionsTable.page, size: this.optionsTable.itemsPerPage })
  }
}
</script>

<style scoped>

</style>