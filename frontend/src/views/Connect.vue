<template>
  <v-container>
    <div 
    class="ml-auto mr-auto mt-10 d-flex justify-space-around align-center"
    align="center"
    justify="center"
    > 
      <v-col cols = "5">
        <v-img
        class="ml-auto mr-auto"   
        min-height="400"
        max-height="400"
        width="400"
        :src="image" 
        contain
        >
        </v-img>
      </v-col>

      <v-spacer></v-spacer>

      <v-col>
        <v-btn
        class = "white--text"
        :class="stateColor[connectState]"
        @click="IoTConnect()"
        >
          {{connectStateMsg[connectState]}}
        </v-btn>
      </v-col>

      <v-spacer></v-spacer>

      <v-col cols = "5">
        <img 
         v-bind:src="'data:image/jpg;charset=utf-8;base64,' + camera"
         class="ml-auto"   
        />
      </v-col>

    </div>
    
    <v-alert
      class="mt-10 mr-auto ml-auto"
      border="left"
      colored-border
      color="rgb(102,51,0)"
      elevation="2"
      max-width="1000"
      v-for="(article, idx) in articles" :key="idx"
      style="background-color: rgba(255,255,255,0.8);"
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
import AxiosService from '../axios/index'
import { AxiosResponse } from 'axios';
import ContentService from '../axios/contentService'
import { io } from "socket.io-client"
import {SOCKET_BASE_URL} from "../config/index"

@Component
export default class Test extends Vue {

  image = "";
  isStreaming = 0;
  articles: [string, string][] = [];
  feedback: [string, string][] = [];
  connectState = 0;
  iotNum = 0;
  stateColor:string [] = ["grey lighten-1", "success", "error", "primary"]
  connectStateMsg:string [] = ["연결 확인", "연결 중...", "연결 실패", "연결 성공"]
  curUrlName = this.$route.name;
  prevUrl = "";
  nextUrl = "";
  camera = "";
  socket = io(SOCKET_BASE_URL)

  async created(){
    const contentId = this.$route.params.contentId;
    const exerciseId = this.$route.params.exerciseId;
 
    const detail: AxiosResponse<[]> = await ContentService.getDetail(contentId)
    //console.log(detail.data)

    this.iotNum = detail.data.iotNum;
    
    this.prevUrl = `/guide/${exerciseId}/${contentId}`;
    this.nextUrl = `/practice/${exerciseId}/${contentId}`;
      
    this.articles.push(["장비 착용 방법", detail.data.iotManual]);
    this.image = require(`@/assets/images/connect/${detail.data.detailId}.jpg`)
  }

  mounted(){

    const contentId = this.$route.params.contentId;
      
    this.socket.emit("imageFTS", "start" )

    this.socket.on('imageSTF', (data) =>{
        //console.log(data)  
        this.camera = new TextDecoder("utf-8").decode(data);
    })
  
    this.socket.on('sensorSTF', (data) =>{
      const ret = JSON.parse(data);
      //console.log(ret);
      if(ret.success){
        if(ret.success == 1) this.connectState = 3;
        else if(ret.success == 0) this.connectState = 2;
      }
    })
  }

    IoTConnect(){
      const contentId = this.$route.params.contentId;
      this.connectState = 1;

      for(let i = 0; i < this.iotNum ; i++) {
        this.socket.emit("sensorFTS", `${contentId}_0${i+1}` )
      }
    }
}

</script>
