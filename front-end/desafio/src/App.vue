<template>
  <v-app>
    <Toolbar />
    <v-content>
      <v-container>
        <transition
          mode="out-in"
          name="slide"
        >
          <router-view />
        </transition>
      </v-container>
    </v-content>
    <v-footer
      app
      style="justify-content: center"
    >
      <span>Wender Galan - Desafio API &copy; 2020</span>
    </v-footer>
  </v-app>
</template>

<script>
import Toolbar from './components/Toolbar'

export default {
  name: 'App',
  components: { Toolbar },
  data: () => ({
    //
  })
}
</script>

<style>
@keyframes slide-in {
  from {
    transform: translateY(-30px);
    opacity: 0;
  }
  to {
    transform: translateY(0px);
    opacity: 1;
  }
}

@keyframes slide-out {
  from {
    transform: translateY(0px);
    opacity: 1;
  }
  to {
    transform: translateY(-30px);
    opacity: 0;
  }
}

.slide-enter-active {
  animation: slide-in 0.3s ease;
}

.slide-leave-active {
  animation: slide-out 0.3s ease;
}
</style>
