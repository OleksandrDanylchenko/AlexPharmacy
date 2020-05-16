<template>
  <div>
    <b-button id="sidebar-button" variant="outline" v-b-toggle.pharmacy-sidebar>
      <i class="fas fa-bars fa-2x" />
    </b-button>
    <b-sidebar id="pharmacy-sidebar" bg-variant="dark" backdrop shadow>
      <div class="px-3 py-2">
        <div class="d-flex justify-content-center flex-wrap">
          <router-link
            to="/"
            tag="button"
            class="btn btn-success rounded-pill w-100 my-2 fadeInLeft"
            v-wow
          >
            <h1 class="m-0 landing-button-text text-danger font-weight-normal">
              Alex Pharmacy
            </h1>
          </router-link>

          <router-link
            to="/cities"
            tag="button"
            class="btn btn-success rounded-pill w-75 my-2 fadeInLeft shiftRight"
            text-decoration-none
            v-wow
          >
            <h3 class="m-0 landing-button-text">Міста</h3>
          </router-link>

          <div class="w-100" />

          <router-link
            to="/manufacturers"
            tag="button"
            class="btn btn-success rounded-pill w-75 my-2 fadeInLeft shiftLeft"
            v-wow
          >
            <h3 class="m-0 landing-button-text">Виробники</h3>
          </router-link>

          <div class="w-100" />

          <router-link
            to="/clients"
            tag="button"
            class="btn btn-success rounded-pill w-75 my-2 fadeInLeft shiftRight"
            v-wow
          >
            <h3 class="m-0 landing-button-text">Клієнти</h3>
          </router-link>

          <div class="w-100" />

          <router-link
            to="/drugs"
            tag="button"
            class="btn btn-success rounded-pill w-75 my-2 fadeInLeft shiftLeft"
            v-wow
          >
            <h3 class="m-0 landing-button-text">Препарати</h3>
          </router-link>

          <div class="w-100" />

          <router-link
            to="/diseases"
            tag="button"
            class="btn btn-success rounded-pill w-75 my-2 fadeInLeft shiftRight"
            v-wow
          >
            <h3 class="m-0 landing-button-text">Хвороби</h3>
          </router-link>

          <div class="w-100" />

          <router-link
            to="/baskets"
            tag="button"
            class="btn btn-success rounded-pill w-75 my-2 fadeInLeft shiftLeft"
            v-wow
          >
            <h3 class="m-0 landing-button-text">Кошики</h3>
          </router-link>

          <div class="w-100" />

          <router-link
            to="/requests"
            tag="button"
            class="btn btn-danger rounded-pill w-50 my-2 fadeInLeft shiftRight"
            v-wow
          >
            <h3 class="m-0 landing-button-text">Запити</h3>
          </router-link>
        </div>
      </div>
    </b-sidebar>
  </div>
</template>

<script>
export default {
  name: "Sidebar"
};
</script>

<style>
#sidebar-button {
  position: absolute;
  top: 10px;
  left: 10px;
}
</style>

<style lang="css">
@import "../../styles/gradient.css";
@import "../../styles/animate.css";
@import "../../styles/menuButtons.css";
</style>
