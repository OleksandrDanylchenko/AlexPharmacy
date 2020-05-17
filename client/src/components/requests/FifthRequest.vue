<template>
  <div>
    <b-button v-b-toggle.request-5 variant="outline-danger" class="w-100">
      <span class="text-primary" style="font-size: 1.3em">
        5. {{ requestText }}
      </span>
    </b-button>
    <b-collapse id="request-5" class="mt-1">
      <b-card>
        <b-card-text>
          <b-form-group>
            <b-input-group prepend="Оберіть DN">
              <b-form-select
                v-model="chosenDrugName"
                :options="drugsNames"
                @change="handleFifthRequest"
              />
            </b-input-group>
          </b-form-group>
          <b-table
            hover
            :items="cities"
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
            <template v-slot:cell(cityName)="data">
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
  name: "FifthRequest",
  props: ["drugsNames"],
  data() {
    return {
      requestText:
        "Знайти назви міст, виробники яких не постачають препарат з назвою DN",
      fields: [
        {
          key: "cityName",
          label: "Назва міста",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        }
      ],
      isBusy: true,
      chosenDrugName: null,
      cities: []
    };
  },
  methods: {
    handleFifthRequest() {
      this.isBusy = true;
      DataService.searchRequest(
        `cities/search/findCitiesManufacturersNotSupplyingDrug?drugName=${this.chosenDrugName}`
      )
        .then(response => {
          this.cities = response.data._embedded.cities;
          this.isBusy = false;
        })
        .catch(error => {
          this.$emit("addError", `${error}`);
        });
    }
  }
};
</script>
