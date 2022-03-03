<template>
  <v-container
   class="mx-auto"
   max-width="1000"
  > 
   <div class="mt-10">
    <v-row>
        <h1 class="ml-10">우리학교 맞춤 과정</h1>
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
        <div>
          <v-card 
            class="ml-5 mr-5 mt-5"
            height="350"
            width="300"
            :href = nextUrl+article[2]
            style="background-color: rgb(201,167,139);"
          >
            <v-row justify="center">
              <img
              class="mt-5"
              height="270"
              :src='article[0]'
              contain
              />
            </v-row>
             <v-card-title class="white--text mt-3">{{article[1]}}</v-card-title>
          </v-card>
        </div>

      </v-slide-item>  
    </v-slide-group>
  </div>

  </v-container>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import ContentService from '../axios/contentService'
import { AxiosResponse } from 'axios'

@Component
export default class Home extends Vue {

  articles:[string, string, number][] = [];
  hobbys:[string, string, number][] = [];
  nextUrl = "/main/"

  async mounted(){
    const user = JSON.parse(localStorage.getItem('user'));

    const book: AxiosResponse<[]> = await ContentService.getBook(user)
    //console.log(book);

    const mainUnit: AxiosResponse<[]> = await ContentService.getMainUnitList(book.data.bookId);
    //console.log(mainUnit);

    for(const i in mainUnit.data){
      this.articles.push([ require(`@/assets/images/mainUnit/${mainUnit.data[i].mainUnitId}.jpg`) , 
                            mainUnit.data[i].title, 
                            mainUnit.data[i].mainUnitId]) 
    }
  }
}
</script>
