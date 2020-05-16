<template>
  <div>
    <b-button v-b-toggle.request-1 variant="outline-success" class="w-100">
      <span class="text-primary">1. {{ requestText }}</span>
    </b-button>
    <b-collapse id="request-1" class="mt-1">
      <b-card>
        <b-card-text>
          <b-form-group>
            <b-input-group prepend="Оберіть DG">
              <b-form-select
                v-model="chosenDrugName"
                :options="drugsNames"
                @change="handleFirstRequest"
              />
            </b-input-group>
          </b-form-group>
          <b-table
            id="clientsTable"
            hover
            :items="manufacturers"
            :fields="fields"
            :busy.sync="isBusy"
            primary-key="id"
            class="m-0"
          >
            <template v-slot:table-busy>
              <div class="text-center text-success mt-1">
                <b-spinner
                  type="grow"
                  class="align-middle"
                  style="width: 1.5rem; height: 1.5rem;"
                />
              </div>
            </template>
            <template v-slot:cell(manufacturerTrademark)="data">
              {{ data.item.trademark }}
            </template>
          </b-table>
        </b-card-text>
      </b-card>
    </b-collapse>
  </div>
</template>

<script>
import DataService from "../../service/DataService";

export default {
  name: "FirstRequest",
  props: ["drugsNames"],
  data() {
    return {
      requestText:
        "Знайти торгові марки постачальників, що постачають препарат DN",
      fields: [
        {
          key: "manufacturerTrademark",
          label: "Торгові марки",
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        }
      ],
      isBusy: true,
      chosenDrugName: null,
      manufacturers: []
    };
  },
  methods: {
    handleFirstRequest() {
      this.isBusy = true;
      DataService.searchRequest(
        `manufacturers/search/findManufacturersBySupplyDrug?drugName=${this.chosenDrugName}`
      )
        .then(response => {
          this.manufacturers = response.data._embedded.manufacturers;
          this.isBusy = false;
        })
        .catch(error => {
          this.$emit("addError", `${error}`);
        });
    }
  }
};
</script>
