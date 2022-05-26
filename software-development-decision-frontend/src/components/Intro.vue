<script>

export default {
    methods: {
    start() {
        this.$emit('started', true)
    },
    }
}

</script>   

<template>
  <div class="introduction">
    <h2>Entscheidungsfindung für die Softwareentwicklungsform</h2>
    <p>Zur Unterstützung bei der Entscheidung über die Softwareentwicklungsform kann dieses Softwaresystem genutzt werden. Bei der Entscheidung muss zwischen No Code, Low Code und klassischer Softwareentwicklung abgewogen werden. Dazu werden die relevanten Einflussfaktoren bei der Entscheidung erfragt und deren Wechselwirkungen miteinbezogen. Am Ende erscheint eine Übersicht mit den aufgelisteten Risiken zu den Softwareentwicklungformen. </p>
    <button @click="start()">Start</button>
  </div>
</template>


<style scoped>
  @import '../assets/variables.css';

button {
    border-bottom-left-radius: 20px;
    border-bottom-right-radius: 20px;
    border-top-right-radius: 20px;
    border-top-left-radius: 20px;
    color: white;
    background-color: var(--primary-color);
    width: 110px;
    font-weight: bold;
}

h2 {
    color: var(--primary-color);
}

  .introduction{
      margin: 50px;
  }

</style>