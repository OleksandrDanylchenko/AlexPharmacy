<template>
  <div>
    <div class="mt-4">
      <MessagesErrorsComponent
        :messages="messages"
        :errors="errors"
        @dismissMessages="dismissMessages"
        @dismissErrors="dismissErrors"
        class="fadeInLeft"
        v-wow
      />
      <b-row>
        <b-col
          class="d-flex justify-content-between align-items-center mb-3 fadeInLeft"
          v-wow
        >
          <h1 class="display-4 text-primary">Список міст-філіалів:</h1>
          <b-button pill variant="outline-success" @click="openCitiesModal(-1)">
            <i class="fas fa-plus-circle" />&nbsp;Додати нове місто
          </b-button>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <div class="fadeInLeft" v-wow>
            <b-table
              id="citiesTable"
              hover
              :items="cities"
              :fields="fields"
              :busy.sync="isBusy"
              primary-key="id"
            >
              <template v-slot:table-busy>
                <div class="text-center text-danger mt-2">
                  <b-spinner
                    type="grow"
                    class="align-middle"
                    style="width: 3rem; height: 3rem;"
                  />
                  <strong>&nbsp;Завантаження таблиці...</strong>
                </div>
              </template>
              <template v-slot:cell(editModal)="data">
                <b-button
                  pill
                  variant="outline-primary"
                  @click="openCitiesModal(data.item.id)"
                >
                  <i class="fa fa-edit" />
                </b-button>
              </template>
              <template v-slot:cell(deleteModal)="data">
                <b-button
                  pill
                  variant="outline-danger"
                  @click="openDeleteModal(data.item.id)"
                >
                  <i class="fa fa-trash" />
                </b-button>
              </template>
            </b-table>
          </div>
        </b-col>
      </b-row>
    </div>
    <CitiesModal
      :processingId="processingId"
      @addCity="addCity"
      @updateCity="updateCity"
      @addError="addError"
    />
    <DeleteModal :processingId="processingId" @deleteRecord="deleteCities" />
  </div>
</template>

<script>
  import MessagesErrorsComponent from "../common/MessagesErrorsComponent";
  import {MessagesErrorsDismissMixin} from "../../mixins/MessagesErrorsDismissMixin";
  import CitiesModal from "./CitiesModal";
  import DeleteModal from "../common/DeleteModal";
  import DataService from "../../service/DataService";

  export default {
  mixins: [MessagesErrorsDismissMixin],
  name: "CitiesComponent",
  components: {
    MessagesErrorsComponent,
    CitiesModal,
    DeleteModal
  },
  data() {
    return {
      fields: [
        {
          key: "id",
          label: "ID",
          sortable: true,
          thClass: "text-danger text-center",
          tdClass: "text-danger text-center",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "name",
          label: "Назва міста",
          sortable: true,
          thClass: "text-center text-primary",
          tdClass: "text-center text-primary",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "editModal",
          label: "Змінити",
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "deleteModal",
          label: "Видалити",
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        }
      ],
      cities: [],
      isBusy: true,
      processingId: Number.MIN_VALUE,
      resource: "cities",

      messages: [],
      errors: []
    };
  },
  methods: {
    refreshCities() {
      this.isBusy = true;
      DataService.retrieveAllRecords(this.resource)
        .then(response => {
          this.$log.debug("Cities loaded: ", response.data);
          this.cities = response.data._embedded.cities;
          this.isBusy = false;
        })
        .catch(error => {
          this.$log.debug(error);
          this.addError(`Сталася помилка завантаження таблиці: `);
          this.addError(error);
        });
    },
    openCitiesModal(id) {
      this.dismissMessages();
      this.dismissErrors();
      this.processingId = id;
      this.$bvModal.show("cityModal");
    },
    addCity(newCity) {
      this.isBusy = true;
      DataService.addRecord(this.resource, newCity)
        .then(() => {
          this.$log.debug("Added city " + newCity);
          this.addMessage(`Нове місто додано успішно`);
          this.refreshCities();
        })
        .catch(error => {
          this.$log.debug(error);
          this.addError(error);
        });
      this.isBusy = false;
      this.$bvModal.hide("cityModal");
    },
    updateCity(updateCities) {
      this.isBusy = true;
      DataService.updateRecord(
        this.resource + "/" + this.processingId,
        updateCities
      )
        .then(() => {
          this.$log.debug("Updated city " + updateCities);
          this.addMessage(`Місто №${updateCities.id} змінено успішно`);
          this.refreshCities();
        })
        .catch(error => {
          this.$log.debug(error);
          this.addError(error);
        });
      this.isBusy = false;
      this.$bvModal.hide("cityModal");
    },
    openDeleteModal(id) {
      this.processingId = id;
      this.dismissMessages();
      this.dismissErrors();
      this.$bvModal.show("deleteModal");
    },
    deleteCities(id) {
      this.isBusy = true;
      DataService.deleteRecord(this.resource, id)
        .then(() => {
          this.$log.debug("Deleted city №" + id);
          this.addMessage(`Видалення міста №${id} виконано успішно`);
          this.refreshCities();
        })
        .catch(error => {
          this.$log.debug(error);
          this.addError(`Видалення міста №${id} не виконано!`);
        });
      this.isBusy = false;
      this.$bvModal.hide("deleteModal");
    }
  },
  created() {
    this.refreshCities();
  }
};
</script>

<style lang="css">
@import "../../styles/gradient.css";
@import "../../styles/animate.css";
</style>
