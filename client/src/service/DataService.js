import axios from "axios";
import { store } from "../../store";

class DataService {
  retrieveAllRecords(resource) {
    return axios.get(store.state.apiUrl + `/${resource}`);
  }
  retrieveRecord(resource, id) {
    return axios.get(store.state.apiUrl + `/${resource}/${id}`);
  }
  addRecord(resource, addForm) {
    return axios.post(store.state.apiUrl + `/${resource}`, addForm);
  }
  updateRecord(resource, updateForm) {
    return axios.put(store.state.apiUrl + `/${resource}`, updateForm);
  }
  deleteRecord(resource, id) {
    return axios.delete(store.state.apiUrl + `/${resource}/${id}`);
  }
}

export default new DataService();
