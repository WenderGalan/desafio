<template>
  <v-data-table
    :calculate-widths="calculateWidths"
    :headers="computedHeaders"
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
        <v-spacer />
        <template>
          <v-btn
            @click="classificarPropostas"
            class="mb-2 mr-4"
            color="primary" dark
          >
            <v-icon
              class="mr-2"
              small
            >
              mdi-sort
            </v-icon>
            Classificar
          </v-btn>
        </template>
        <v-dialog max-width="1000px" v-model="dialog">
          <template v-slot:activator="{ on }">
            <v-btn
              class="mb-2"
              color="primary"
              dark v-on="on"
            >
              <v-icon
                class="mr-2"
                small
              >
                mdi-plus
              </v-icon>
              Proposta
            </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" md="12" sm="12">
                    <v-text-field
                      :rules="[() => !!record.fornecedor || 'Este campo é requirido']"
                      autofocus clearable
                      label="Fornecedor *"
                      outlined
                      ref="fornecedor"
                      required v-model="record.fornecedor"
                    />
                  </v-col>
                  <v-col cols="12" md="6" sm="6">
                    <v-text-field
                      :rules="[() => !!record.preco || 'Este campo é requirido']" clearable
                      label="Preço *"
                      outlined
                      prefix="R$"
                      ref="preco"
                      required
                      type="number" v-model.number="record.preco"
                    />
                  </v-col>
                  <v-col cols="12" md="6" sm="6">
                    <v-text-field
                      :disabled="licitacao.tipoClassificacao !== 'NOTA_PRECO'"
                      :label="licitacao.tipoClassificacao !== 'NOTA_PRECO' ? 'Nota' : 'Nota *'"
                      :required="licitacao.tipoClassificacao === 'NOTA_PRECO'"
                      :rules="[() => !!record.nota || 'Este campo é requirido', () => record.nota >= 0 && record.nota <= 10 || 'A nota deve ser um valor entre 0 e 10']"
                      clearable
                      outlined
                      ref="nota"
                      type="number"
                      v-model.number="record.nota"
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
    <template v-slot:item.nota="{ item }">
      <v-chip :color="getColor(item.nota)" dark>
        {{ item.nota }}
      </v-chip>
    </template>
    <template v-slot:item.preco="{ item }">
      <span>R$ {{ parseFloat(item.preco).toFixed(2) }}</span>
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
    </template>
    <template v-slot:no-data>
      <span>Não há registros, tente </span>
      <a @click="findPropostas">atualizar.</a>
    </template>
  </v-data-table>
</template>

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
      if (item > 6) {
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