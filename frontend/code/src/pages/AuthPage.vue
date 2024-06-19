<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '@/services/api/players'
import useUser from '@/composables/useUser'
import useToast from '@/composables/useToast'
import { login } from '@/services/api/auth'

const router = useRouter()
const registerMode = ref(false)
const formData = ref({
    email: '',
    name: '',
    password: ''
})
const position_on_court = ref('')
const { showToast } = useToast()
const { setUser } = useUser()

const handleAuth = async (event) => {
    event.preventDefault()
    try {
        if (registerMode.value) {
            const response = await register({ ...formData.value, positions_on_court: [position_on_court.value] })
            if (response.status === 202) {
                showToast({ message: `Te has registrado correctamente`, error: false })
                setUser(response.id)
                registerMode.value = false
            }
            router.push('/auth')
        } else {
            const response = await login({...formData.value })
            if (response.status === 200)
                showToast({ message: `Has iniciado sesion correctamente`, error: false })
            router.push('/')
        }
    } catch (error) {
        showToast({ message: error.response.data.description, error: true })
        console.error(error)
    }
}

</script>

<template>
    <section class="flex justify-center items-center h-screen">
        <form class="card-bordered w-full max-w-md p-6 space-y-4">
            <h2 v-if="!registerMode" class="card text-2xl font-bold">Iniciar sesion</h2>
            <h2 v-else class="card text-2xl font-bold">Registro</h2>
            <p v-if="!registerMode" class="font-sans">Ingresa tu correo electrónico y contraseña para acceder a tu cuenta.</p>
            <p v-else class="font-sans">Ingresa tus datos para crear una nueva cuenta.</p>
            
            <div class="card space-y-2">
                <div class="label">
                    <span class="label-text">Correo electrónico</span>
                </div>
                <input v-model="formData.email" type="email" placeholder="ejemplo@dominio.com" class="input input-bordered w-full max-w-xs" />
                
                <div v-if="registerMode" class="label">
                    <span class="label-text">Nombre de usuario</span>
                </div>
                <input v-if="registerMode" v-model="formData.name" type="text" placeholder="Ingresa tu nombre" class="input input-bordered w-full max-w-xs" />

                <div class="label">
                    <span class="label-text">Contraseña</span>
                </div>
                <input v-model="formData.password" type="password" placeholder="******" class="input input-bordered w-full max-w-xs" />

                <div v-if="registerMode" class="label">
                    <span class="label-text">Posicion en pista</span>
                </div>
                <select v-if="registerMode" v-model="position_on_court" class="select select-bordered w-full max-w-xs">
                    <option disabled selected value="">Selecciona tu posicion</option>
                    <option value="PG">Point Guard</option>
                    <option value="SG">Shooting Guard</option>
                    <option value="SF">Small Forward</option>
                    <option value="PF">Power Forward</option>
                    <option value="C">Center</option>
                </select>
                
                <p v-if="!registerMode">
                    ¿No tienes una cuenta todavía? 
                    <span @click="registerMode = true" class="font-bold underline cursor-pointer">Regístrate</span>
                </p>
                <div class="card-actions !mt-5">
                    <button v-if="!registerMode" type="submit" @click="handleAuth" class="btn !btn-wide btn-success ">
                        Iniciar sesión
                    </button>
                    <div v-else class="justify-between space-x-28">
                        <button @click="registerMode = false" class="btn btn-neutral">
                            Cancelar
                        </button>
                        <button type="submit" @click="handleAuth" class="btn btn-success">
                            Registrarse
                        </button>
                    </div>
                </div>
            </div>
        </form>
    </section>
</template>