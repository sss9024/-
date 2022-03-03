<template>
  <!-- 소개, 가이드 페이지로 사용 -->
  <!-- 장비 연결, 자세 연습, 실습 페이지로 사용 -->
  <v-container>
    <div 
    class="ml-auto mr-auto mt-10 d-flex justify-space-around align-center"
    align="center"
    justify="center"
    >
      <v-col cols = "5">
        <v-carousel
          cycle
          hide-delimiter-background
          hide-delimiters
          interval="2000"
          :show-arrows = "false"
          style="height: 450px;"
        >
          <v-carousel-item
            v-for="(content, i) in contents"
            :key="i"
          >
            <v-img
              class="ml-auto mr-auto"   
              min-height="400"
              max-height="400"
              :src="content.image" 
              contain
            >
            </v-img>
            <div class="text-center mt-5">
              <span>{{i+1}} / {{totActionNum}} </span>
            </div>
          </v-carousel-item>
        </v-carousel>
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
         min-height="400"
        />
      </v-col>
    </div>

    <v-row
    align="center"
    justify="center">
      <v-btn 
      class="white--text"
      :class="stateColor[testState]"
      @click="startTest()"
      >
        {{testBtnMsg}}
      </v-btn>
    </v-row>

    <v-row
    class = "mt-10"
    align="center"
    justify="space-around">
      <v-btn v-for="(content, idx) in contents" :key="idx"
      class="white--text"
      :class="stateColor[content.state]"
      >
        동작 {{idx+1}}번
      </v-btn>
    </v-row>
    
    <v-alert
      class="mt-10 mr-auto ml-auto"
      border="left"
      colored-border
      color="rgb(102,51,0)"
      elevation="2"
      max-width="1000"
      style="background-color: rgba(255,255,255,0.8);"
    >
      <div class="title">
        {{help.title}}
      </div>
      <v-divider></v-divider>
      <div class="mt-5">{{help.content}}</div>
      <div class="mt-3">{{processingMsg}}</div>
      <div v-if = "curActionNum > 0" class="mt-3">
        <span
         class="mr-3"
         v-for="(content, idx) in contents" :key="idx"
        >
          {{content.msg}}
        </span>
      </div>
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

  contents = [];
  help = {
    "title" : "",
    "content" : ""
  };
  processingMsg = "";
  connectState = 0;
  testState = 0;
  testBtnMsg = "";
  totActionNum = 0;
  curActionNum = 0;
  iotNum = 0;
  stateColor:string [] = ["grey lighten-1", "success", "error", "primary"]
  connectStateMsg:string [] = ["연결 확인", "연결 중...", "연결 실패", "연결 성공"]
  curUrlName = this.$route.name;
  prevUrl = "";
  nextUrl = "";
  camera = "";
  socket = io(SOCKET_BASE_URL)
  scoreSum = 0;

  async created(){
    const exerciseId = this.$route.params.exerciseId;
    const contentId = this.$route.params.contentId;
 
    const detail: AxiosResponse<[]> = await ContentService.getDetail(contentId)
    //console.log(detail.data)

    this.curActionNum = 1;
    this.totActionNum = detail.data.actionNum;
    this.iotNum = detail.data.iotNum;

    this.prevUrl = `/practice/${exerciseId}/${contentId}`;
    this.nextUrl = `/score/${exerciseId}/${contentId}`

    for(let i = 0; i < this.totActionNum ; i++) {
      const tmp = {
          "image" : require(`@/assets/images/practice/${detail.data.detailId}_${i+1}.jpg`),
          "state" : 0,
          "msg" : "",
          "score" : 0,
      }
      this.contents.push(tmp);
    } 

    //console.log(this.contents)
    this.help.title = "도움말"
    this.help.content = "위의 실습 시작 버튼을 클릭하면 실습이 시작됩니다. 실습이 완료되면 점수 페이지로 이동됩니다."
    this.testBtnMsg = "실습 시작" 
    }

    mounted(){

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

        if(ret.actnum){
          this.contents[this.curActionNum-1].score = Math.round((parseFloat(ret.pitch)+parseFloat(ret.roll))/2 * 100)
          if(ret.ispass == 0){
            this.contents[ret.actnum].state = 2;
            this.processingMsg = `동작 ${this.curActionNum}번이 맞지 않아요. 다시 한 번 해볼까요?`;
            if(this.contents[this.curActionNum-1].score > 0){
              this.contents[this.curActionNum-1].msg = `동작 ${this.curActionNum}번 점수 : 
                                                      ${this.contents[this.curActionNum-1].score}점`
            }
          } else if(ret.ispass == 1){
            this.contents[ret.actnum].state = 3;
            this.scoreSum += this.contents[this.curActionNum-1].score;
            console.log(this.scoreSum)
            this.processingMsg = `동작 ${this.curActionNum}번이 맞았어요. 다음 동작을 해볼까요?`;
            this.contents[this.curActionNum-1].msg = `동작 ${this.curActionNum}번 점수 : 
                                                      ${this.contents[this.curActionNum-1].score}점`
          }

          if(ret.actnum == this.totActionNum-1 && ret.ispass == 1) {
            this.testState = 3;
            this.testBtnMsg = "실습 완료"

            const totScore = Math.round(this.scoreSum / 3)
            
            const exerciseId = this.$route.params.exerciseId;
            const contentId = this.$route.params.contentId;
            // 점수 계산 필요
            location.href=`/score/${exerciseId}/${contentId}?score=${totScore}`
          }
        }  
      })
    }

    IoTConnect(){
      this.connectState = 1;
      const contentId = this.$route.params.contentId;

      for(let i = 0; i < this.iotNum ; i++) {
        this.socket.emit("sensorFTS", `${contentId}_0${i+1}` )
      }
    }

    startTest(){
      const contentId = this.$route.params.contentId;
      //console.log("실습 시작");
      this.testState = 1;
      this.testBtnMsg = "실습 중..."
      //console.log(this.testState)
      this.help.content = `위의 그림과 같이 순서대로 동작을 시도하면서 완료되면 다음 동작을 취해주세요`;

      this.socket.emit("sensorFTS", `${contentId}_20` )
    }

}

</script>
