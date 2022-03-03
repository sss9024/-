<template>
  <!-- 스코어 페이지로 사용 -->
  <v-container 
  class =" mr-auto ml-auto"
  max-width="1200">
    <div 
     class="text-h2 mt-10 text-center"
    >
      나의 점수는?
    </div>

    <div 
     class="text-h1 mt-10 mr-auto ml-auto text-center blue--text font-weight-black"
    >
      {{score}}점
    </div>

    <div 
     class="text-h4 mt-10 mr-auto ml-auto text-center font-weight-bold"
    >
      잘하고 있어요!!
    </div>

    <v-row
      class="mt-10 justify-space-around"
      align="center"
    >
      <v-btn
        :href="testUrl"
        color="primary"
        class="white--text"
      >
        <v-icon>mdi-emoticon-wink-outline</v-icon>
        <span class="ml-2">다시 도전하기</span>
      </v-btn>

      <v-btn
        :href="practiceUrl"
        color="success"
        class="white--text"
      >
        <v-icon>mdi-emoticon-confused-outline</v-icon>
        <span class="ml-2">조금 더 연습하기</span>
      </v-btn>

      <v-btn
        :href="homeUrl"
        color="error"
        class="white--text"
      >
        <v-icon>mdi-home-import-outline</v-icon>
        <span class="ml-2">오늘은 여기까지</span>
      </v-btn>
    </v-row>
  </v-container>
</template>

<script lang="ts">
import {Component, Vue} from 'vue-property-decorator'
import AxiosService from '../axios/index'
import { AxiosResponse } from 'axios';

@Component
export default class Score extends Vue {
  // 페이지 생성 시 DB에서 가져와야되는 것 : 보여주는 이미지
  // 장비 연결의 경우 착용 부위에 대한 설명도 필요
  // 촬영되야 되는 이미지가 필요한 경우 컴포넌트를 하나 추가적으로 생성
  // 자세 비교 시 진행에 맞춰 피드백도 가져와야 됨 -> 미정

  image = "";
  articles: [string, string][] = [];
  exerciseId = this.$route.params.exerciseId;
  contentId = this.$route.params.contentId;
  testUrl = `/test/${this.exerciseId}/${this.contentId}`;
  practiceUrl = `/practice/${this.exerciseId}/${this.contentId}`;
  homeUrl = "/home"
  score = this.$route.query.score;

  created(){
    //console.log(this.$route.query);
  }


}
</script>