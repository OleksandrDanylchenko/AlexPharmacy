<template>
  <div id="app">
    <link
      rel="stylesheet"
      href="https://use.fontawesome.com/releases/v5.2.0/css/all.css"
      integrity="sha384-hWVjflwFxL6sNzntih27bfxkr27PmbbK/iSvJ+a4+0owXq79v+lsFkW54bOGbiDQ"
      crossorigin="anonymous"
    />
    <a
      id="copyright"
      class="text-primary"
      href="https://github.com/OlexanderD/AlexPharmacy"
    >
      Alexander Danylchenko, Databases Work 2, 2020
    </a>
    <b-container>
      <router-view />
    </b-container>
  </div>
</template>

<script>
export default {
  name: "App"
};
</script>

<style>
#copyright {
  position: absolute;
  top: 10px;
  right: 10px;
  writing-mode: vertical-lr;
  text-orientation: mixed;
  text-decoration: none;
}
</style>
