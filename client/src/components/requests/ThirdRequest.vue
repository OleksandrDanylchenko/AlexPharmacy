<template>
  <div>
    <b-button v-b-toggle.request-3 variant="outline-danger" class="w-100">
      <span class="text-primary" style="font-size: 1.3em">
        3. {{ requestText }}
      </span>
    </b-button>
    <b-collapse id="request-3" class="mt-1">
      <b-card>
        <b-card-text>
          <b-form-group>
            <b-input-group prepend="Оберіть TM">
              <b-form-select
                v-model="chosenManufacturerTrademark"
                :options="manufacturersTrademarks"
                @change="handleThirdRequest"
              />
            </b-input-group>
          </b-form-group>
          <b-table
            hover
            :items="diseases"
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
            <template v-slot:cell(diseaseName)="data">
              {{ data.item.name }}
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
  name: "ThirdRequest",
  props: ["manufacturersTrademarks"],
  data() {
    return {
      requestText:
        "Знайти назви хвороб, які лікуються препаратами виробника з торговою маркою TM",
      fields: [
        {
          key: "diseaseName",
          label: "Назва хвороби",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        }
      ],
      isBusy: true,
      chosenManufacturerTrademark: null,
      diseases: []
    };
  },
  methods: {
    handleThirdRequest() {
      this.isBusy = true;
      DataService.searchRequest(
        `diseases/search/findDiseasesByDrugsFromManufacturer?manufacturerTrademark=${this.chosenManufacturerTrademark}`
      )
        .then(response => {
          this.diseases = response.data._embedded.diseases;
          this.isBusy = false;
        })
        .catch(error => {
          this.$emit("addError", `${error}`);
        });
    }
  }
};
</script>
