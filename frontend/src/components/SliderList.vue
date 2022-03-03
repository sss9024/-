<template>
  <div class="mt-10">
    <v-row>
        <h1 class="ml-10">{{title}}</h1>
    </v-row>
    <v-slide-group
      class="pa-4"
      active-class="success"
      show-arrows
    >
      <v-slide-item
        v-for="(article, idx) in articles"
        :key="idx"

      >
        <div v-if= "depth < 3">
          <v-card 
            class="ml-5 mr-5 mt-5"
            height="350"
            width="300"
            style="background-color: rgb(201,167,139);"
            @click="sendSig(article[2])"
          >
            <v-row justify="center">
              <img
              class="mt-5"
              height="270"
              :src='article[0]'
              contain
              />
            </v-row>
            <v-card-subtitle class="white--text mt-3">{{article[1]}}</v-card-subtitle>
          </v-card>
        </div>

        <div v-else>
          <v-card 
            class="ml-5 mr-5 mt-5"
            height="350"
            width="300"
            style="background-color: rgb(201,167,139);"
            :href = nextUrl+article[2]
          >
          <v-row justify="center">
              <img
              class="mt-5"
              height="270"
              :src='article[0]'
              contain
              />
            </v-row>
          <v-card-subtitle class="white--text mt-3">{{article[1]}}</v-card-subtitle>
          </v-card>
        </div>

      </v-slide-item>  
    </v-slide-group>
  </div>
</template>

<script lang="ts">
import {Component, Vue, Prop, Emit, Watch} from 'vue-property-decorator'
import { AxiosResponse } from 'axios';
import ContentService from '../axios/contentService'

@Component
export default class SliderList extends Vue {
  @Prop() id: number; 
  @Prop() depth: number;

  title = "";
  articles:[string, string, number][] = [];
  nextUrl = `/guide/${this.$route.params.exerciseId}/`

  mounted(){
     this.getData();
  }

  @Watch('id')
  update(){
      this.getData();
  }

  async getData(){
      if(this.depth == 1){
          const primaryUnit: AxiosResponse<[]> = await ContentService.getPrimaryUnitList(this.id);
          const mainUnit: AxiosResponse<[]> = await ContentService.getMainUnitTitle(this.id)

          this.title = mainUnit.data.title

          for(const i in primaryUnit.data){
            this.articles.push([ require(`@/assets/images/primaryUnit/${primaryUnit.data[i].primaryId}.jpg`) , 
                                primaryUnit.data[i].title, 
                                primaryUnit.data[i].primaryId]) 
          }
          //console.log(this.articles);
        
      } else if(this.depth == 2){
          const primaryUnit: AxiosResponse<[]> = await ContentService.getPrimaryUnitTitle(this.id)
          const subUnit: AxiosResponse<[]> = await ContentService.getSubUnitList(this.id)

          this.title = primaryUnit.data.title;
          
          this.articles.length = 0;
          for(const i in subUnit.data){
              this.articles.push([require(`@/assets/images/subUnit/${subUnit.data[i].subId}.jpg`), 
                                  subUnit.data[i].title, 
                                  subUnit.data[i].subId])
          }
         
      } else if(this.depth == 3){
          const detail: AxiosResponse<[]> = await ContentService.getDetailList(this.id);
          const subUnit: AxiosResponse<[]> = await ContentService.getSubUnitTitle(this.id);
          
          this.title = subUnit.data.title;
          
          this.articles.length = 0;
          for(const i in detail.data){
              this.articles.push([ require(`@/assets/images/detail/${detail.data[i].detailId}.jpg`), 
                                  detail.data[i].title, 
                                  detail.data[i].detailId])
          }
      }
  }

  async sendSig(id){
    //   console.log(this.depth); 
      if(this.depth == 3){
          // router를 이용하여 /guide/exerciseId/contetnId로 이동 
          const exerciseId = this.$route.params.exerciseId;
          const contentId = id;
          //this.$router.push(`/guide/${exerciseId}/${contentId}`)
      } else {
          // emit을 이용하여 SliderList를 추가해야 한다는 신호를 보냄
          // 현재 depth와 id를 보냄
          //console.log(id);
          this.$emit("curDepth", this.depth, id )
      }
    
  }
}
</script>