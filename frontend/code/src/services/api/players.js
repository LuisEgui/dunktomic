import { http } from "../http";
import { authUserService } from "../auth-user/auth-user-service";

export const register = async ({name, email, password, positions_on_court }) => {
    const { data } = await http().post('/players', {
        name,
        email,
        password,
        positions_on_court,
    })
    console.log(data)
}