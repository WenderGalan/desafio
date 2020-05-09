<template src="./LicitacaoTemplate.html" />
<script>
export default {
  name: 'Licitacao',
  data: () => ({
    loadingText: 'Aguarde, está carregando os dados.',
    loadingData: false,
    calculateWidths: true,
    dialog: false,
    headers: [
      {
        text: 'Descrição',
        align: 'start',
        value: 'descricao',
        widths: '50%',
        sortable: false
      },
      {
        text: 'Tipo de Classificação',
        value: 'tipoClassificacao',
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
    items: [],
    editedIndex: -1,
    formHasErrors: false,
    record: {
      descricao: '',
      tipoClassificacao: ''
    },
    defaultRecord: {
      descricao: '',
      tipoClassificacao: ''
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
      resource: '/licitacoes'
    },
    tipoClassificacaoItems: [
      {
        value: 'NOTA_PRECO',
        text: 'Nota Preço'
      },
      {
        value: 'MENOR_PRECO',
        text: 'Menor Preço'
      }
    ]
  }),
  /**
   */
  computed: {
    formTitle () {
      return this.editedIndex === -1 ? 'Incluir Licitação' : 'Alterar Licitação'
    },
    form () {
      return {
        name: this.record.descricao,
        telephone: this.record.tipoClassificacao
      }
    }
  },
  /**
   */
  watch: {
    dialog (val) {
      val || this.close()
    }
  },
  /**
   */
  methods: {
    /**
     */
    getLabelTipoClassificacao (tipoClassificacao) {
      let index = this.tipoClassificacaoItems.findIndex((item) => item.value === tipoClassificacao)
      if (index > -1) {
        return this.tipoClassificacaoItems[index].text
      }
      return ''
    },
    /**
     */
    findLicitacoes ({ page, size }) {
      this.loadingData = true
      this.$http.get(`${this.service.resource}`, { params: { page, size } })
        .then((response) => {
          this.loadingData = false
          this.serverItemsLength = response.data.totalElements
          this.items = response.data.content
        })
        .catch(() => (this.loadingData = false))
    },
    /**
     */
    updatePagination (pagination) {
      this.findLicitacoes({ page: pagination.page, size: pagination.itemsPerPage })
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
    propostasItem (item) {
      this.$router.push({
        name: 'propostas',
        params: {
          licitacao: item
        }
      })
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
  }
}
</script>

<style scoped>
</style>