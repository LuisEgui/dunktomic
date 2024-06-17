<script setup>
import { ref } from 'vue'

const registerMode = ref(false)

</script>

<template>
    <div class="flex justify-center items-center h-screen">
        <form class="card-bordered w-full max-w-md p-6 space-y-4" @submit="onSubmit">
            <h2 v-if="!registerMode" class="card text-2xl font-bold">Iniciar sesion</h2>
            <h2 v-else class="card text-2xl font-bold">Registro</h2>
            <p v-if="!registerMode" class="font-sans">Ingresa tu correo electrónico y contraseña para acceder a tu cuenta.</p>
            <p v-else class="font-sans">Ingresa tus datos para crear una nueva cuenta.</p>
            
            <div class="card space-y-2">
                <div class="label">
                    <span class="label-text">Correo electrónico</span>
                </div>
                <input type="email" placeholder="ejemplo@dominio.com" class="input input-bordered w-full max-w-xs" />
                
                <div v-if="registerMode" class="label">
                    <span class="label-text">Nombre de usuario</span>
                </div>
                <input v-if="registerMode" type="text" placeholder="Ingresa tu nombre" class="input input-bordered w-full max-w-xs" />

                <div class="label">
                    <span class="label-text">Contraseña</span>
                </div>
                <input type="password" placeholder="******" class="input input-bordered w-full max-w-xs" />

                <div v-if="registerMode" class="label">
                    <span class="label-text">Posicion en pista</span>
                </div>
                <select v-if="registerMode" class="select select-bordered w-full max-w-xs">
                    <option disabled selected>Selecciona tu posicion</option>
                    <option>Point Guard</option>
                    <option>Shooting Guard</option>
                    <option>Small Forward</option>
                    <option>Power Forward</option>
                    <option>Center</option>
                </select>
                
                <p v-if="!registerMode">
                    ¿No tienes una cuenta todavía? 
                    <span @click="registerMode = true" class="font-bold underline">Regístrate</span>
                </p>
                <div class="card-actions !mt-5">
                    <button v-if="!registerMode" type="submit" class="btn btn-wide btn-success ">
                        Iniciar sesión
                    </button>
                    <div v-else class="justify-between space-x-28">
                        <button @click="registerMode = false" class="btn btn-neutral">
                            Cancelar
                        </button>
                        <button type="submit" class="btn btn-success">
                            Registrarse
                        </button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</template>