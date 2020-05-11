<template>
  <div>
    <div class="d-flex justify-content-center flex-wrap">
      <div class="display-3 text-center mt-3 text-primary">
        Адміністративна панель<br />
        аптечної мережі<br />
        Alex Pharmacy
      </div>

      <router-link
        to="/cities"
        tag="button"
        class="btn btn-success rounded-pill w-75 mt-4 mb-3"
      >
        <h2 class="m-0">Міста-філіали</h2>
      </router-link>

      <router-link
        to="/manufacturers"
        tag="button"
        class="btn btn-success rounded-pill w-75 my-3"
      >
        <h2 class="m-0">Виробники</h2>
      </router-link>

      <router-link
        to="/clients"
        tag="button"
        class="btn btn-success rounded-pill w-75 my-3"
      >
        <h2 class="m-0">Клієнти</h2>
      </router-link>

      <router-link
        to="/drugs"
        tag="button"
        class="btn btn-success rounded-pill w-75 my-3"
      >
        <h2 class="m-0">Препарати</h2>
      </router-link>

      <router-link
        to="/diseases"
        tag="button"
        class="btn btn-success rounded-pill w-75 my-3"
      >
        <h2 class="m-0">Хвороби</h2>
      </router-link>

      <router-link
        to="/baskets"
        tag="button"
        class="btn btn-success rounded-pill w-75 my-3"
      >
        <h2 class="m-0">Кошики</h2>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "Landing"
};
</script>

<style lang="css">
@import "../styles/gradient.css";
</style>
