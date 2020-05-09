<template>
  <v-card
    dark
    flat
    tile
  >
    <v-toolbar
      dark
      dense
      elevation="5"
    >
      <v-toolbar-title
        class="headline text-uppercase mr-4"
      >
        <span>DESAFIO</span>
        <span class="font-weight-light">API</span>
      </v-toolbar-title>
      <v-spacer />
      <v-toolbar-items>
        <v-btn
          to="/"
        >Licitações
        </v-btn>
        <v-btn
          to="/sobre"
        >Sobre
        </v-btn>
      </v-toolbar-items>
    </v-toolbar>
  </v-card>
</template>

<script>
export default {
  name: 'Toolbar'
}
</script>

<style scoped>
</style>