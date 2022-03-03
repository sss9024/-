<template>
  <!-- 선택 페이지로 사용  -->
  <v-container
   class="mx-auto"
   max-width="1000"
  > 

    <SliderList v-for="(urlId, idx) in urlIds" :key='idx' 
    :id="urlId" :depth="idx+1" @curDepth="confirmDepth">
    </SliderList> 
  </v-container>

</template>

<script lang="ts">
import {Component, Vue, Watch} from 'vue-property-decorator'
import SliderList from '@/components/SliderList.vue'
import { component } from 'vue/types/umd';

@Component({
  components: {
    SliderList
  }
})
export default class Selection extends Vue {
    // 페이지 생성 시 mainUnit(대단원)id 와 1단계라는 정보를 보냄
    // 상위 항목 중 하나가 선택되면 DB에서 하위 항목을 가져옴
    // 최하위 항목이 선택되었을 경우 가이드 페이지로 이동 -> /guide/운동id/항목id
    
    urlIds:number[] = []
    curUrlId = this.$route.params.exerciseId;
    totalDepth = 1;
    
    async created(){
      //console.log(this.$route.params.exerciseId);
      this.urlIds.push(parseInt(this.$route.params.exerciseId));
      //console.log(this.urlIds) 
    }

    confirmDepth(depth, id){
      if(depth == this.totalDepth) {
        this.totalDepth++;
        this.urlIds.push(id);
        //console.log(this.urlIds)
      } else if(depth < this.totalDepth){
        this.totalDepth = depth+1;
        // console.log(this.urlIds.length)
        while(this.urlIds.length > depth) this.urlIds.pop();
        this.urlIds.push(id);
        //console.log(this.urlIds);
      }
    }
    
}

</script>


