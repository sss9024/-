<template>
  <!-- 소개, 가이드 페이지로 사용 -->
  <v-container>
    <v-img 
      class="ml-auto mr-auto mt-10"
      height="300"
      width="400"
      :src='image'
      contain
    >
    </v-img>

    <v-alert
      class="mt-5 mr-auto ml-auto"
      border="left"
      colored-border
      color="rgb(102,51,0)"
      elevation="2"
      max-width="1000"
      style="background-color: rgba(255,255,255,0.8);"
      v-for="(article, idx) in articles" :key="idx"
    >
      <div class="title">
        {{article[0]}}
      </div>
      <v-divider></v-divider>
      <div class="mt-5">{{article[1]}}</div>
    </v-alert>

    <v-row
      class="mt-5"
      align="center"
      justify="space-between"
    >
      <v-btn
        :href="this.prevUrl"
        text
      >
        <v-icon>mdi-chevron-left</v-icon>
        <span class="mr-2">prev</span>
      </v-btn>

      <v-btn
        :href="this.nextUrl"
        text
      >
        <span class="mr-2">next</span>
        <v-icon>mdi-chevron-right</v-icon>
      </v-btn>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator'
import { AxiosResponse } from 'axios'
import ContentService from '../axios/contentService'

@Component
export default class Description extends Vue {
    image = "";
    articles: [string, string][] = [];
    curUrlName = this.$route.name;
    prevUrl = "";
    nextUrl = "";

    async created(){
      //console.log(this.curUrlName);
      if(this.curUrlName == "ExerciseMain") {
        const exerciseId = this.$route.params.exerciseId;
        this.prevUrl = `/`;
        this.nextUrl = `/sel/${exerciseId}`

        const mainUnit: AxiosResponse<[]> = await ContentService.getMainUnit(exerciseId);
   
        this.articles.push(["소개글", mainUnit.data.intro ]);
        this.articles.push(["향상 능력", mainUnit.data.improvement ]);
        this.image = require(`@/assets/images/mainUnit/${mainUnit.data.mainUnitId}.jpg`)
      } else {
        const exerciseId = this.$route.params.exerciseId;
        const contentId = this.$route.params.contentId;
        
        this.prevUrl = `/sel/${exerciseId}`;
        this.nextUrl = `/connect/${exerciseId}/${contentId}`

        const detail: AxiosResponse<[]> = await ContentService.getDetail(contentId);
       
        this.articles.push(["목표", detail.data.objective]);
        this.articles.push(["자세", detail.data.posture ]);
        this.articles.push(["Tip", detail.data.tip ]);
        this.image = require(`@/assets/images/detail/${detail.data.detailId}.jpg`)
      }  
    }
}
</script>
