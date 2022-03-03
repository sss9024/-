<template>
  <v-app>
    <div v-if = 'loggedIn'>
    <Header></Header>
    </div>
    <v-main style="background-color: rgba(201,167,139,0.7);">
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import Header from "@/components/Header.vue";

@Component({
  components: {
    Header
  }
})
export default class Selection extends Vue {

  loggedIn = this.$store.state.auth.status.loggedIn

}
</script>
