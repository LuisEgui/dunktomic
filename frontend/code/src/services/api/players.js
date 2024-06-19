import { http } from "../http";
import axios from "axios";

export const register = async ({ name, email, password, positions_on_court }) => {
    try {
      console.log('Sending request to register user:', { name, email, password, positions_on_court });
      const { data } = await http().post('/players', {
        name,
        email,
        password,
        positions_on_court,
      });
      console.log('Received response:', data);
      return data;
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.error('Axios error:', error.response ? error.response.data : error.message);
      } else {
        console.error('Unexpected error:', error);
      }
      throw error; // Vuelve a lanzar el error para manejarlo en otro lugar si es necesario
    }
  };