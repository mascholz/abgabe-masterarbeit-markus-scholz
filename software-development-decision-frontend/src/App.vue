<template>
  <div id="app">
    <div v-if="!surveyStarted">
      <Intro @started="onSurveyStarted"></Intro>
    </div>
    <div v-if="!surveyFinished && surveyStarted">
      <Survey @responseSurvey="surveyResults" />
    </div>
    <div v-if="surveyFinished">
      <Result :message=resultJson />
    </div>
  </div>
</template>

<script>
import Survey from './components/Survey.vue'
import Result from './components/Result.vue'
import Intro from './components/Intro.vue'

export default {
  name: 'App',
  components: {
    Survey,
    Result,
    Intro
  },
  data() {
    return {
      surveyStarted: false,
      surveyFinished: false,
      resultJson:
      {
        "risks": [],
        "opened": [],
        "conclusionResultList": [],
        "effort": null,
      },
    }
  },

  methods: {
    surveyResults(mess){
      this.resultJson.risks = mess.data.risks;
      this.resultJson.conclusionResultList = mess.data.conclusionResultList;
      this.resultJson.effort = mess.data.effort;
      this.surveyFinished = true;
    },
    onSurveyStarted(){
      this.surveyStarted = true;
    },
  }
}
</script>
