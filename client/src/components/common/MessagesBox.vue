<template>
  <div
    class="text-center flex-grow-1 alert alert-success alert-dismissible fade show"
  >
    <dl>
      <dt v-for="(message, index) in messages" :key="index">{{ message }}</dt>
    </dl>
    <button
      type="button"
      class="close"
      data-dismiss="alert"
      @click="dismissMessages"
    >
      &times;
    </button>
  </div>
</template>

<script>
export default {
  props: ["messages"],
  methods: {
    dismissMessages() {
      this.$emit("dismissMessages");
    }
  }
};
</script>
