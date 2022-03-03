<template>
  <v-app-bar app color="grey lighten-1" dark
    src="../assets/app_bar_background_grey.png"
    contain
  >
    <template v-slot:img="{ props }">
        <v-img
          v-bind="props"
          aspect-ratio="10"
        ></v-img>
    </template>

    <div class="d-flex align-center mr-5">
      <a href="/home">
        <v-img
          alt="Vuetify Logo"
          class="shrink mr-2"
          contain
          src="../assets/main_logo.png"
          transition="scale-transition"
          width="80"
        />
      </a>

    <v-toolbar-title class="font-weight-bold mr-5 black--text">{{ title }}</v-toolbar-title>
    </div>

    <v-row v-if='this.menuType == 2' class="align-content-center">
      <v-flex class="text-center" v-for="(menu, idx) in menus" :key="idx" >
        <div v-if="active == idx" class= "blue--text font-weight-bold">{{menu}}</div>
        <div v-else class="black--text font-weight-bold">{{menu}}</div>
      </v-flex>
    </v-row>

    <v-spacer></v-spacer>

    <v-menu
      bottom
      left
      offset-y
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          dark
          icon
          v-bind="attrs"
          v-on="on"
        >
          <v-icon color="grey">mdi-account</v-icon>
        </v-btn>
      </template>

      <v-list>
        <v-list-item>
          <v-btn href="/my" text>
            마이 페이지
          </v-btn>
        </v-list-item>
        <v-list-item>
          <v-btn text
          @click="logout()"
          >
            로그 아웃
          </v-btn>
        </v-list-item>
      </v-list>
    </v-menu>

    <v-btn href="/about" text>
      <v-icon color="grey">mdi-help</v-icon>
    </v-btn>
  </v-app-bar>
</template>

<script lang="ts">
import {Component, Vue, Watch} from 'vue-property-decorator'
import ContentService from '../axios/contentService';

@Component
export default class Header extends Vue{
    title = "";
    curUrlName = ""
    menus: string[] = ["소개", "선택", "가이드", "연결", "연습", "실습", "점수"];
    menuType = 0;
    active = -1;

    @Watch('curUrlName')
    update(){
        this.setTypeAndActive()
    }

    created(){
      this.curUrlName = this.$route.name;
      //console.log(this.curUrlName)
      this.setTypeAndActive()
      //console.log(this.active)
    }

    async setTypeAndActive(){
      if(this.curUrlName == "Home"){
        this.title = "운동 못하니?"
      } else if(this.curUrlName == "About"){
        this.title = "도움말"
      } else if(this.curUrlName == "User"){
        this.title = "마이 페이지"
        this.menuType = 1;
      } else {

        this.menuType = 2;

        if(this.curUrlName == "ExerciseMain") this.active = 0;
        else if(this.curUrlName == "ExerciseSel") this.active = 1;
        else if(this.curUrlName == "ContentMain") this.active = 2;
        else if(this.curUrlName == "Connect") this.active = 3;
        else if(this.curUrlName == "Practice") this.active = 4;
        else if(this.curUrlName == "Test") this.active = 5;
        else if(this.curUrlName == "Score") this.active = 6;

        if(this.active < 2){
          const mainUnit: AxiosResponse<[]> = await ContentService.
          getMainUnitTitle(this.$route.params.exerciseId)

          this.title = mainUnit.data.title;
        } else {
          const detail: AxiosResponse<[]> = await ContentService.
          getDetailTitle(this.$route.params.contentId)
          
          this.title = detail.data.title;
        }
      }
    }

    logout(){
      this.$store.dispatch('auth/logout');
      location.href='/';
    }
    
}
</script>
