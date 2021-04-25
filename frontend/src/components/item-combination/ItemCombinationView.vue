<<script lang="ts">
 import { Component, Model, PropSync, Vue, Watch } from 'vue-property-decorator';
 import ItemCombinationQuestionDetails from '@/models/management/questions/ItemCombinationQuestionDetails';
 @Component
 export default class ItemCombinationView extends Vue {
   @PropSync('questionDetails', { type:ItemCombinationQuestionDetails })
   sQuestionDetails!: ItemCombinationQuestionDetails;
 }
 </script>