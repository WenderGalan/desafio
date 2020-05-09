<template>
  <v-data-table
    :calculate-widths="calculateWidths"
    :headers="headers"
    :items="items"
    :loading="loadingData"
    :loading-text="loadingText"
    :options.sync="optionsTable"
    :server-items-length="serverItemsLength"
    @pagination="updatePagination"
    class="elevation-1"
    sort-by="calories"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-spacer></v-spacer>
        <v-dialog max-width="1000px" v-model="dialog">
          <template v-slot:activator="{ on }">
            <v-btn class="mb-2" color="primary" dark v-on="on">
              <v-icon
                class="mr-2"
                small
              >
                mdi-plus
              </v-icon>
              Licitação
            </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" md="8" sm="6">
                    <v-text-field
                      :rules="[() => !!record.descricao || 'Este campo é requirido']"
                      autofocus clearable
                      label="Descrição *"
                      outlined
                      ref="descricao"
                      required v-model="record.descricao"
                    />
                  </v-col>
                  <v-col cols="12" md="4" sm="6">
                    <v-select
                      :items="tipoClassificacaoItems"
                      :rules="[() => !!record.tipoClassificacao || 'Este campo é requirido']"
                      clearable
                      label="Tipo de Classificação *"
                      outlined
                      ref="tipoClassificacao"
                      v-model="record.tipoClassificacao"
                    />
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn @click="close" color="blue darken-1" text>Cancelar</v-btn>
              <v-btn @click="save" color="blue darken-1" text>Salvar</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:item.tipoClassificacao="{ item }">
      <span>{{ getLabelTipoClassificacao(item.tipoClassificacao) }}</span>
    </template>
    <template v-slot:item.actions="{ item }">
      <v-tooltip bottom>
        <template #activator="{ on }">
          <v-icon
            @click="editItem(item)"
            class="mr-4"
            v-on="on"
          >
            mdi-pencil
          </v-icon>
        </template>
        <span>Editar</span>
      </v-tooltip>
      <v-tooltip bottom>
        <template #activator="{ on }">
          <v-icon
            @click="deleteItem(item)"
            class="mr-4"
            v-on="on"
          >
            mdi-delete
          </v-icon>
        </template>
        <span>Deletar</span>
      </v-tooltip>
      <v-tooltip bottom>
        <template #activator="{ on }">
          <v-icon
            @click="propostasItem(item)"
            class="mr-4"
            v-on="on"
          >
            mdi-cloud-print
          </v-icon>
        </template>
        <span>Propostas</span>
      </v-tooltip>
    </template>
    <template v-slot:no-data>
      <span>Não há registros, tente </span>
      <a @click="findLicitacoes">atualizar.</a>
    </template>
  </v-data-table>
</template>
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