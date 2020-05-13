<template>
  <div>
    <b-row v-if="messages.length > 0">
      <b-col>
        <MessagesBox :messages="messages" @dismissMessages="dismissMessages" />
      </b-col>
    </b-row>
    <b-row v-if="errors.length > 0">
      <b-col>
        <ErrorsBox :errors="errors" @dismissErrors="dismissErrors" />
      </b-col>
    </b-row>
  </div>
</template>

<script>
import MessagesBox from "./MessagesBox";
import ErrorsBox from "./ErrorsBox";

export default {
  components: { ErrorsBox, MessagesBox },
  props: ["messages", "errors"],
  methods: {
    dismissMessages() {
      this.$emit("dismissMessages");
    },
    dismissErrors() {
      this.$emit("dismissErrors");
    }
  }
};
</script>

<style>
dl {
  margin-bottom: 0;
}
</style>
