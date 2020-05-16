<template>
  <b-modal id="deleteModal" hide-header>
    <template v-slot:default>
      <b-row>
        <b-col>
          <img
            src="https://i.ibb.co/rd7bMFp/red-Trash-Can.png"
            class="rounded mx-auto d-block"
            alt="trashBin"
          />
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <h3 class="text-center">
            Ви впевненні, що бажаєте видалити даний запис?
          </h3>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <h5 class="text-center">Надалі його не можна буде відновити.</h5>
        </b-col>
      </b-row>
    </template>
    <template v-slot:modal-footer="{ cancel }">
      <b-button variant="primary" @click="cancel()">
        Скасувати
      </b-button>
      <b-button variant="danger" @click="deleteRecord">
        Видалити
      </b-button>
    </template>
  </b-modal>
</template>

<script>
export default {
  props: ["processingId"],
  methods: {
    deleteRecord() {
      this.$emit("deleteRecord", this.processingId);
    }
  }
};
</script>
