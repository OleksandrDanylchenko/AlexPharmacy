<template>
  <div>
    <div class="display-4 text-center">
      Адміністративна панель<br />
      аптечної мережі<br />
      Alex Pharmacy
    </div>

    <router-link
      to="/cities"
      tag="button"
      class="btn btn-success rounded-pill w-100 my-2"
    >
      <h2 class="m-0">Міста-філіали</h2>
    </router-link>
    <router-link
      to="/cities"
      tag="button"
      class="btn btn-success rounded-pill w-100 my-2"
    >
      <h2 class="m-0">Міста-філіали</h2>
    </router-link>
  </div>
</template>

<script>
export default {
  name: "Landing"
};
</script>
