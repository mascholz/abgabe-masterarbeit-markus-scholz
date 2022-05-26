<template>
   <div v-if="loaded">
      <Survey :survey="survey" />
   </div>
</template>

<script>
import axios from 'axios';
import 'survey-vue/modern.min.css';
import { Survey, StylesManager, Model } from 'survey-vue';


StylesManager.ThemeColors['modern']['$main-color'] = 'rgb(52,67,83)';
StylesManager.ThemeColors['modern']['$main-hover-color'] = 'rgb(52,67,83)';
StylesManager.ThemeColors['modern']['$answer-background-color'] = 'rgb(217,226,233, 0.5)';
// StylesManager.ThemeColors['modern']['$header-color'] = '#ff0000';
// StylesManager.ThemeColors['modern']['$header-background-color'] = '#ff0000';
// StylesManager.ThemeColors['modern']['$body-container-background-color'] = '#ff0000';
// StylesManager.ThemeColors['modern']['$error-color'] = '#ff0000';
// StylesManager.ThemeColors['modern']['$error-background-color'] = '#ff0000';

StylesManager.applyTheme("modern");


export default {
   name: 'MyFirstSurvey',
   components: {
      Survey
   },

   data() {
      const survey = null;

      return {
         survey,
         loaded: false,
      }
   },

   created() {
      axios.get(`http://localhost:8080/software-development-decision-0.0.1-SNAPSHOT/api/questions`)
         .then(response => {
            this.surveyJson = response.data
            
            const surveyInt = new Model(this.surveyJson);
            surveyInt.showQuestionNumbers = "off";
            surveyInt.onComplete.add(this.alertResults);
            surveyInt.locale = "de";
            surveyInt.showProgressBar = "top";
            surveyInt.completedHtml = "<h3>Bitte warten</h3>";
            
            this.survey = surveyInt;
            this.loaded = true;
         })
         .catch(e => {
            this.errors.push(e)
         })
   },
   methods: {
      alertResults(sender) {
         
         axios.post('http://localhost:8080/software-development-decision-0.0.1-SNAPSHOT/api/surveyResult', {
            body: sender.data
         })
         .then((data) => {
            this.$emit("responseSurvey", data);         
         })
      },

   },
}
</script>