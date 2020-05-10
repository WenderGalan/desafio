export default {
  data: () => ({
    items: [],
    loadingText: 'Aguarde, está carregando os dados.',
    editedIndex: -1,
    optionsTable: {
      page: 1,
      itemsPerPage: 10,
      pageStart: 1,
      pageStop: 1,
      pageCount: 1,
      itemsLength: 10,
      rowsPerPageItems: [1, 2, 4, 8, 16]
    }
  }),
  methods: {
    /**
     */
    updatePagination (pagination) {
      this.findItems({ page: pagination.page, size: pagination.itemsPerPage })
    },
    /**
     */
    validateForm (formHasErrors, form, refs) {
      return new Promise(((resolve, reject) => {
        formHasErrors = false
        Object.keys(form).forEach(f => {
          if (!form[f]) {
            formHasErrors = true
          }
          if (refs[f]) {
            refs[f].validate(true)
          }
        })
        if (!formHasErrors) {
          return resolve()
        }
        return reject()
      }))
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
    editItem (item) {
      this.editedIndex = this.items.indexOf(item)
      this.record = Object.assign({}, item)
      this.dialog = true
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
     * Chamado após validar o form
     */
    beforeSave () {
    },
    /**
     */
    save () {
      this.validateForm(this.formHasErrors, this.form, this.$refs)
        .then(() => {
          this.beforeSave()
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