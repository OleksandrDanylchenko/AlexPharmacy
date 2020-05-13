<template>
  <div>
    <b-modal
      id="instructionModal"
      @show="loadValues"
      @hidden="resetData"
      hide-footer
    >
      <template v-slot:modal-title>
        <h3>{{ drugData.name }}</h3>
      </template>

      <template v-slot:default>
        {{ drugData.instruction }}
      </template>
    </b-modal>
  </div>
</template>

<script>
import DataService from "../../service/DataService";

export default {
  props: ["processingId"],
  name: "InstructionModal",
  data() {
    return {
      drugData: {
        name: null,
        instruction: null
      },
      drugsResource: "drugs"
    };
  },
  methods: {
    loadValues() {
      this.$nextTick(() => {
        DataService.retrieveRecord(this.drugsResource, this.processingId)
          .then(response => {
            this.$log.debug("Loaded data of drug №" + this.processingId);
            this.drugData.name = response.data.name;
            this.drugData.instruction = response.data.instruction;
          })
          .catch(error => {
            this.$log.debug(error);
            this.$emit("addError", `${error}`);
            this.$bvModal.hide("drugModal");
          });
        this.drugData.id = this.processingId;
      });
    },
    resetData() {
      this.drugData = {
        name: null,
        instruction: null
      };
    }
  }
};
</script>
