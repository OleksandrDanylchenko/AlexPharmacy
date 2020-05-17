<template>
  <div>
    <b-button v-b-toggle.request-8 variant="outline-danger" class="w-100">
      <span class="text-primary" style="font-size: 1.3em">
        8. {{ requestText }}
      </span>
    </b-button>
    <b-collapse id="request-8" class="mt-1">
      <b-card>
        <b-card-text>
          <b-table
            hover
            :items="cities"
            :fields="fields"
            :busy.sync="isBusy"
            primary-key="id"
            class="m-0"
          >
            <template v-slot:table-busy>
              <div class="text-center text-danger mt-1">
                <b-spinner
                  type="grow"
                  class="align-middle"
                  style="width: 1.5rem; height: 1.5rem;"
                />
              </div>
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
  name: "SeventhRequest",
  data() {
    return {
      requestText: "Знайти назви міст виробників, які постачають усі препарати",
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
      cities: []
    };
  },
  methods: {
    handleEightRequest() {
      this.isBusy = true;
      DataService.searchRequest(
        `cities/search/findCitiesManufacturersSupplyAllDrugs`
      )
        .then(response => {
          this.cities = response.data._embedded.cities;
          this.isBusy = false;
        })
        .catch(error => {
          this.$emit("addError", `${error}`);
        });
    }
  },
  created() {
    this.handleEightRequest();
  }
};
</script>
