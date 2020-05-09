<template src="./PropostaTemplate.html" />

<script>
export default {
  name: 'Proposta',
  data: () => ({
    items: [],
    loadingText: 'Aguarde, está carregando os dados.',
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
    editedIndex: -1,
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
    optionsTable: {
      page: 1,
      itemsPerPage: 10,
      pageStart: 1,
      pageStop: 1,
      pageCount: 1,
      itemsLength: 10,
      rowsPerPageItems: [1, 2, 4, 8, 16]
    },
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
    updatePagination (pagination) {
      this.findPropostas({ page: pagination.page, size: pagination.itemsPerPage })
    },
    /**
     */
    findPropostas ({ page, size }) {
      this.loadingData = true
      this.$http.get(`${this.service.serviceLicitacao}/${this.licitacao.id}/propostas`, { params: { page, size } })
        .then((response) => {
          this.loadingData = false
          this.serverItemsLength = response.data.totalElements
          this.items = response.data.content
        })
        .catch(() => (this.loadingData = false))
    },
    /**
     */
    editItem (item) {
      this.editedIndex = this.items.indexOf(item)
      this.record = Object.assign({}, item)
      this.dialog = true
    },
    /**
     */
    deleteItem (item) {
      const index = this.items.indexOf(item)
      if (confirm('Você tem certeza que deseja deletar este registro?')) {
        this.$http.delete(`${this.service.resource}/${this.items[index].id}`)
          .then(() => {
            this.items.splice(index, 1)
            this.updatePagination({ page: this.optionsTable.page, size: this.optionsTable.itemsPerPage })
          })
      }
    },
    /**
     */
    close () {
      this.dialog = false
      setTimeout(() => {
        this.record = Object.assign({}, this.defaultRecord)
        this.editedIndex = -1
      }, 300)
    },
    /**
     */
    validateForm () {
      return new Promise(((resolve, reject) => {
        this.formHasErrors = false
        Object.keys(this.form).forEach(f => {
          if (!this.form[f]) {
            this.formHasErrors = true
          }
          if (this.$refs[f]) {
            this.$refs[f].validate(true)
          }
        })
        if (!this.formHasErrors) {
          return resolve()
        }
        return reject()
      }))
    },
    /**
     */
    save () {
      this.validateForm()
        .then(() => {
          // Atribui a licitacao antes de salvar ou editar
          this.record.licitacao = this.licitacao
          if (this.editedIndex > -1) {
            this.$http.put(`${this.service.resource}/${this.record.id}`, this.record)
              .then((response) => {
                Object.assign(this.items[this.editedIndex], response.data)
                this.close()
              })
          }
          else {
            this.$http.post(`${this.service.resource}`, this.record)
              .then((response) => {
                this.record.id = response.data.id
                this.updatePagination({ page: this.optionsTable.page, size: this.optionsTable.itemsPerPage })
                this.close()
              })
          }
        })
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