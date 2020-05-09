<template src="./SobreTemplate.html" />

<script>
export default {
  name: 'Sobre',
  data: () => ({
    urlLogo: 'https://github.githubassets.com/images/modules/logos_page/Octocat.png'
  }),
  methods: {
    openGithub () {
      window.open('https://github.com/WenderGalan/desafio/tree/desafio_Wender_Galan', '_blank')
    }
  }
}
</script>

<style scoped>
.img :hover {
  cursor: pointer;
  align-items: center;
}
</style>