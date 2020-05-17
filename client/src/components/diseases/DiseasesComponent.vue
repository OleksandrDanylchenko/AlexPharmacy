<template>
  <div>
    <div class="mt-4">
      <Sidebar />
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
          <h1 class="display-4 text-primary">Список хвороб:</h1>
          <b-button
            pill
            variant="outline-success"
            @click="openDiseaseModal(-1)"
          >
            <i class="fas fa-plus-circle"></i>&nbsp;Додати нову хворобу
          </b-button>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <div class="fadeInLeft" v-wow>
            <b-table
              id="diseasesTable"
              hover
              :items="diseases"
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
              <template v-slot:cell(suitableDrugs)="data">
                <b-button
                  size="sm"
                  variant="outline-dark"
                  v-b-toggle="'drugs' + data.item.id"
                >
                  Відкрити список
                </b-button>
                <b-collapse :id="'drugs' + data.item.id" class="mt-2">
                  <b-list-group>
                    <b-list-group-item
                      v-for="drug in data.item.drugs"
                      :key="drug.id"
                      class="my-1 py-1 px-0"
                    >
                      <div class="d-table m-auto">Код: {{ drug.id }}</div>
                      <div class="d-table m-auto">Назва: {{ drug.name }}</div>
                    </b-list-group-item>
                  </b-list-group>
                </b-collapse>
              </template>
              <template v-slot:cell(editModal)="data">
                <b-button
                  pill
                  variant="outline-primary"
                  @click="openDiseaseModal(data.item.id)"
                >
                  <i class="fa fa-edit"></i>
                </b-button>
              </template>
              <template v-slot:cell(deleteModal)="data">
                <b-button
                  pill
                  variant="outline-danger"
                  @click="openDeleteModal(data.item.id)"
                >
                  <i class="fa fa-trash"></i>
                </b-button>
              </template>
            </b-table>
          </div>
        </b-col>
      </b-row>
    </div>
    <DiseaseModal
      :processingId="processingId"
      @addDisease="addDisease"
      @updateDisease="updateDisease"
      @addError="addError"
    />
    <DeleteModal :processingId="processingId" @deleteRecord="deleteDisease" />
  </div>
</template>

<script>
import MessagesErrorsComponent from "../common/MessagesErrorsComponent";
import { MessagesErrorsDismissMixin } from "../../mixins/MessagesErrorsDismissMixin";
import DiseaseModal from "./DiseaseModal";
import DeleteModal from "../common/DeleteModal";
import DataService from "../../service/DataService";
import Sidebar from "../common/Sidebar";

export default {
  mixins: [MessagesErrorsDismissMixin],
  name: "DiseasesComponent",
  components: {
    Sidebar,
    MessagesErrorsComponent,
    DiseaseModal,
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
          label: "Назва",
          sortable: true,
          thClass: "text-center",
          tdClass: "text-center",
          thStyle: "vertical-align: middle;"
        },
        {
          key: "suitableDrugs",
          label: "Підходящі ліки",
          thClass: "text-center",
          tdClass: "text-center",
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
      diseases: [],
      isBusy: true,
      processingId: Number.MIN_VALUE,
      diseasesResource: "diseases",

      messages: [],
      errors: []
    };
  },
  methods: {
    refreshDiseases() {
      this.isBusy = true;
      DataService.retrieveAllRecords(this.diseasesResource)
        .then(response => {
          this.$log.debug(
            "Diseases loaded: ",
            response.data._embedded.diseases
          );
          response.data._embedded.diseases.forEach(disease => {
            DataService.searchRequest(`diseases/${disease.id}/drugs`).then(
              drugsData => {
                let diseaseRecord = {
                  id: disease.id,
                  name: disease.name,
                  drugs: drugsData.data._embedded.drugs
                };
                this.diseases.push(diseaseRecord);
              }
            );
          });
          this.isBusy = false;
        })
        .catch(error => {
          this.$log.debug(error.response.data.message);
          this.addError(`Сталася помилка завантаження таблиці: `);
          this.addError(error.response.data.message);
        });
    },
    openDiseaseModal(id) {
      this.dismissMessages();
      this.dismissErrors();
      this.processingId = id;
      this.$bvModal.show("diseaseModal");
    },
    addDisease(newDisease) {
      this.isBusy = true;
      DataService.addRecord(this.diseasesResource, newDisease)
        .then(() => {
          this.$log.debug("Added disease " + newDisease);
          this.addMessage(`Нову хворобу додано успішно`);
          this.refreshDiseases();
        })
        .catch(error => {
          this.$log.debug(error.response.data.message);
          this.addError(error.response.data.message);
        });
      this.isBusy = false;
      this.$bvModal.hide("diseaseModal");
    },
    updateDisease(updateDisease) {
      this.isBusy = true;
      DataService.updateRecord(
        this.diseasesResource + "/" + this.processingId,
        updateDisease
      )
        .then(() => {
          this.$log.debug("Update disease " + updateDisease);
          this.addMessage(`Хворобу №${updateDisease.id} змінено успішно`);
          this.refreshDiseases();
        })
        .catch(error => {
          this.$log.debug(error.response.data.message);
          this.addError(error.response.data.message);
        });
      this.isBusy = false;
      this.$bvModal.hide("diseaseModal");
    },
    openDeleteModal(id) {
      this.processingId = id;
      this.dismissMessages();
      this.dismissErrors();
      this.$bvModal.show("deleteModal");
    },
    deleteDisease(id) {
      this.isBusy = true;
      DataService.deleteRecord(this.diseasesResource, id)
        .then(() => {
          this.$log.debug("Deleted disease №" + id);
          this.addMessage(`Видалення хвороби №${id} виконано успішно`);
          this.refreshDiseases();
        })
        .catch(error => {
          this.$log.debug(error.response.data.message);
          this.addError(`Видалення хвороби №${id} не виконано!`);
        });
      this.isBusy = false;
      this.$bvModal.hide("deleteModal");
    }
  },
  created() {
    this.refreshDiseases();
  }
};
</script>

<style lang="css">
@import "../../styles/gradient.css";
@import "../../styles/animate.css";
</style>
