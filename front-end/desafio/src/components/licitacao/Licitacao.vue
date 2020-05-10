<template src="./LicitacaoTemplate.html" />
<script>
import Form from '../../app/Mixins/Form'

export default {
  name: 'Licitacao',
  mixins: [Form],
  data: () => ({
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
    findItems ({ page, size }) {
      this.loadingData = true
      this.$http.get(`${this.service.resource}`, { params: { page, size } })
        .then((response) => {
          this.loadingData = false
          this.serverItemsLength = response.data.totalElements
          this.items = response.data.content
        })
        .finally(() => (this.loadingData = false))
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
    }
  }
}
</script>

<style scoped>
</style>