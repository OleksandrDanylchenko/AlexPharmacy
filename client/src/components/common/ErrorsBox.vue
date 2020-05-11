<template>
  <div
    class="text-center flex-grow-1 alert alert-danger alert-dismissible fade show"
  >
    <dl>
      <dt v-for="(error, index) in errors" :key="index">{{ error }}</dt>
    </dl>
    <button
      type="button"
      class="close"
      data-dismiss="alert"
      @click="dismissErrors"
    >
      &times;
    </button>
  </div>
</template>

<script>
export default {
  props: ["errors"],
  methods: {
    dismissErrors() {
      this.$emit("dismissErrors");
    }
  }
};
</script>
