<template>
  <v-container fluid>
    <v-row align="center">
      <v-col
        class="d-flex"
        cols="12"
        sm="6"
      >
        <v-select
          :items="items"
          label="item"
          dense
          outlined
        ></v-select>
      </v-col>


 </v-row>
      <v-select
            v-model="value"
            :items="items"
            label="items relacionados"
            multiple
          >
            <template v-slot:selection="{ item, index }">
              <v-chip v-if="index === 0">
                <span>{{ item }}</span>
              </v-chip>
              <span
                v-if="index === 1"
                class="grey--text caption"
              >
                (+{{ value.length - 1 }} others)
                </span>
                 </template>
                    </v-select>
                  </v-container>

  </v-container>
</template>





<<script lang="ts">
 import { Component, Model, PropSync, Vue, Watch } from 'vue-property-decorator';
 import ItemCombinationQuestionDetails from '@/models/management/questions/ItemCombinationQuestionDetails';
 @Component
 export default class ItemCombinationCreate extends Vue {
   @PropSync('questionDetails', { type:ItemCombinationQuestionDetails })
   sQuestionDetails!: ItemCombinationQuestionDetails;
 }
 </script>