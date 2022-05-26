<script>

export default {
  props: {
    message: Object,
  },
  
  data() {
    return {
      resultJson: this.message,
      arrow: require('@/assets/expand-arrow.png'),
      lowIcon: require('@/assets/low.png'),
      mediumIcon: require('@/assets/medium.png'),
      highIcon: require('@/assets/high.png'),
      einfachIcon: require('@/assets/einfach.png'),
      kompliziertIcon: require('@/assets/kompliziert.png'),
      komplexIcon: require('@/assets/komplex.png'),
      chaotischIcon: require('@/assets/chaotisch.png'),
      wichtigIcon: require('@/assets/wichtig.png'),
      unsicherIcon: require('@/assets/unsicher.png'),
    }
  },

  methods: {
    toggle(id) {
      const index = this.resultJson.opened.indexOf(id);
      if (index > -1) {
        this.resultJson.opened.splice(index, 1)
      } else {
        this.resultJson.opened.push(id)
      }
    },

    getEffortImg(effortId){
      if(effortId == 1){
        return this.lowIcon;
      }
      else if(effortId == 2){
        return this.mediumIcon;
      }
      else {
        return this.highIcon;
      }
    },

    getComplexityImg(complexity){
      if(complexity == "einfach"){
        return this.einfachIcon;
      } else if(complexity == "kompliziert"){
        return this.kompliziertIcon;
      } else if(complexity == "komplex"){
        return this.komplexIcon;
      } else {
        return this.chaotischIcon;
      }
    },

    getImportanceImg(importance){
      if(importance == true){
        return this.wichtigIcon;
      }
    },

    getUncertaintyImg(uncertainty){
      if(uncertainty == true){
        return this.unsicherIcon;
      }
    }
  }
}
</script>

<template>
<div>
  <div class="intoduction">
    <h2>Auswertung</h2>
    <p>Nachfolgend können die Risiken, Aufwand und resultierende Folgen auf Basis der beantworteten Fragen eingesehen werden.</p>
  </div>
  <h3>Risiken</h3>
  <div class="table-responsive" id="resultTable">
    <table class="table table-hover">
      <thead>
        <tr class="heading">
          <th class="basic"></th>
          <th class="top-left">No Code</th>
          <th>Low Code</th>
          <th class="top-right">Code</th>
        </tr>
      </thead>
      <tbody v-for="risk in resultJson.risks" :key="risk.id">
        <tr @click="toggle(risk.id)" :class="{ opened: resultJson.opened.includes(risk.id)}">
          <th class="heading ">{{ risk.name }}<img class="arrow" :src="arrow"></th>
          <td ><img class="risk" :src="getComplexityImg(risk.noCodeComplexity)"></td>
          <td><img class="risk" :src="getComplexityImg(risk.lowCodeComplexity)"></td>
          <td><img class="risk" :src="getComplexityImg(risk.codeComplexity)"></td>
        </tr>
        <tr v-if="resultJson.opened.includes(risk.id)">
          <td><img class="risk" :src="getImportanceImg(risk.importance)"> <img class="risk" :src="getUncertaintyImg(risk.uncertainty)"></td>
          <td class="open">{{ risk.noCodeRisk }}</td>
          <td class="open">{{ risk.lowCodeRisk }}</td>
          <td class="open">{{ risk.codeRisk }}</td>
        </tr>
      </tbody>
    </table>
    <p><img class="legend" :src="einfachIcon"> = einfach; <img class="legend" :src="kompliziertIcon"> = kompliziert; <img class="legend" :src="komplexIcon"> = komplex; <img class="legend" :src="chaotischIcon"> = chaotisch</p>
    <p><img class="legend" :src="wichtigIcon"> = Die Charakteristik ist sehr wichtig; <img class="legend" :src="unsicherIcon"> = Bei der Beantwortung der Fragen der Charakteristik war ich mir unsicher</p>
  </div>

  <h3>Aufwand</h3>
    <div class="table-responsive">
      <table class="table">
        <tbody>
          <tr>
            <th class="heading">Bewertung</th>
            <td><img class="effort" :src="getEffortImg(resultJson.effort.noCode)"></td>
            <td><img class="effort" :src="getEffortImg(resultJson.effort.lowCode)"></td>
            <td><img class="effort" :src="getEffortImg(resultJson.effort.code)"></td>
          </tr>
        </tbody>
      </table>
    </div>

<h3>Resultierende Folgen</h3>
  <div class="table-responsive">
    <table class="table">
      <tbody>
        <tr v-for="conclusionResult in resultJson.conclusionResultList" :key="conclusionResult">
          <th class="heading">{{ conclusionResult.name }}</th>
          <td class="conclusion">{{ conclusionResult.noCode }}</td>
          <td class="conclusion">{{ conclusionResult.lowCode }}</td>
          <td class="conclusion">{{ conclusionResult.code }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</div>
</template>

<style scoped>
  @import '../assets/variables.css';

th {
  text-align: center;
  color: white;
}

th:hover {
  color: white !important;
}

td {
  width: 25%;
  height: 50px;
  text-align: center;
}


h3 {
  margin-top: 50px;
  margin-left: 50px;
}

.heading {
  background-color: var(--primary-color);
  vertical-align: middle;
}

.basic {
  background-color: white;
}

.top-right {
  border-top-right-radius: 20px;
}

.top-left {
  border-top-left-radius: 20px;
}

.bottom-right {
  border-bottom-right-radius: 20px;
}

.bottom-left {
  border-bottom-left-radius: 20px;
}

.open {
  border-bottom-left-radius: 20px;
  border-bottom-right-radius: 20px;
  background-color: var(--secondary-color);
  text-align: justify;
}

.opened {
  background-color: var(--tertiary-color);
}

.table-responsive {
  margin-left: 50px;
  margin-right: 50px;
}

.arrow {
  float:right;
  height: 25px;
}

.effort {
  height: 75px;
}

.risk {
  height: 50px;
}

.legend {
  height: 35px;
}

.intoduction{
  margin-top: 50px;
  margin-left: 50px;
}

.conclusion{
  text-align: justify;
}
</style>