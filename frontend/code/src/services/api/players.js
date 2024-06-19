import { http } from "../http";
import axios from "axios";
import { localStorageService } from "../local-storage";

export const register = async ({ name, email, password, positions_on_court }) => {
  const response = await http().post('/players', {
    name,
    email,
    password,
    positions_on_court,
  });

  const userId = response.headers['x-resource-id'];
  localStorageService.setItem('user-id', userId);
  return { id: userId, status: response.status };
};