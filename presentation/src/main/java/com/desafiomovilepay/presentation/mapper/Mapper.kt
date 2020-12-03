package com.desafiomovilepay.presentation.mapper

interface Mapper<Dto, Model> {
    fun mapFromDto(dto: Dto): Model
}