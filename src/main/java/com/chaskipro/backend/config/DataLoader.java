package com.chaskipro.backend.config;

import com.chaskipro.backend.entity.*;
import com.chaskipro.backend.repository.ComunaRepository;
import com.chaskipro.backend.repository.ProfessionalProfileRepository;
import com.chaskipro.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProfessionalProfileRepository professionalProfileRepository;
    private final ComunaRepository comunaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            log.info("🚀 Inicializando datos de prueba...");
            
            // Verificar que existan comunas
            List<Comuna> comunas = comunaRepository.findAll();
            if (comunas.isEmpty()) {
                log.warn("⚠️ No hay comunas disponibles. Creando comunas de ejemplo...");
                crearComunasEjemplo();
                comunas = comunaRepository.findAll();
            }

            // Crear profesionales de ejemplo
            crearProfesionales(comunas);
            crearProfesionalesMasivos(comunas);
            
            // Crear cliente de ejemplo
            crearCliente();
            
            log.info("✅ Datos de prueba cargados exitosamente");
            log.info("📊 Total usuarios: {}", userRepository.count());
            log.info("👷 Total profesionales: {}", professionalProfileRepository.count());
        } else {
            log.info("ℹ️ Base de datos ya contiene datos. Omitiendo inicialización.");
        }
    }

    private void crearComunasEjemplo() {
        List<Comuna> comunas = Arrays.asList(
            Comuna.builder().nombre("Santiago Centro").region("Región Metropolitana").build(),
            Comuna.builder().nombre("Providencia").region("Región Metropolitana").build(),
            Comuna.builder().nombre("Las Condes").region("Región Metropolitana").build(),
            Comuna.builder().nombre("Ñuñoa").region("Región Metropolitana").build(),
            Comuna.builder().nombre("Maipú").region("Región Metropolitana").build(),
            Comuna.builder().nombre("La Florida").region("Región Metropolitana").build(),
            Comuna.builder().nombre("Puente Alto").region("Región Metropolitana").build(),
            Comuna.builder().nombre("San Miguel").region("Región Metropolitana").build(),
            Comuna.builder().nombre("Estación Central").region("Región Metropolitana").build(),
            Comuna.builder().nombre("Recoleta").region("Región Metropolitana").build()
        );
        comunaRepository.saveAll(comunas);
        log.info("✅ Creadas {} comunas de ejemplo", comunas.size());
    }

    private void crearProfesionales(List<Comuna> comunas) {
        // Profesional 1: Electricista
        User electricista = User.builder()
            .email("carlos.rojas@chaskipro.cl")
            .password(passwordEncoder.encode("password123"))
            .nombreCompleto("Carlos Rojas Muñoz")
            .rut("16789012-3")
            .rol(Rol.PROFESIONAL)
            .activo(true)
            .build();
        
        electricista = userRepository.save(electricista);
        
        ProfessionalProfile perfilElectricista = ProfessionalProfile.builder()
            .user(electricista)
            .biografia("Electricista certificado con más de 10 años de experiencia. Especializado en instalaciones residenciales y comerciales. Certificación SEC al día.")
            .telefono("+56912345678")
            .categoria(ProfessionCategory.ELECTRICISTA)
            .estadoValidacion(EstadoValidacion.APROBADO)
            .promedioCalificacion(new BigDecimal("4.8"))
            .totalCalificaciones(127)
            .serviciosCompletados(145)
            .coberturas(new HashSet<>(comunas.subList(0, 5)))
            .build();
        
        professionalProfileRepository.save(perfilElectricista);
        log.info("✅ Creado profesional: {}", electricista.getNombreCompleto());

        // Profesional 2: Plomero
        User plomero = User.builder()
            .email("miguel.torres@chaskipro.cl")
            .password(passwordEncoder.encode("password123"))
            .nombreCompleto("Miguel Torres Soto")
            .rut("17890123-4")
            .rol(Rol.PROFESIONAL)
            .activo(true)
            .build();
        
        plomero = userRepository.save(plomero);
        
        ProfessionalProfile perfilPlomero = ProfessionalProfile.builder()
            .user(plomero)
            .biografia("Maestro gasfiter con 15 años de experiencia. Especialista en reparaciones de urgencia, instalación de cañerías y solución de filtraciones. Disponible 24/7.")
            .telefono("+56923456789")
            .categoria(ProfessionCategory.GASFITER)
            .estadoValidacion(EstadoValidacion.APROBADO)
            .promedioCalificacion(new BigDecimal("4.9"))
            .totalCalificaciones(203)
            .serviciosCompletados(218)
            .coberturas(new HashSet<>(comunas.subList(1, 7)))
            .build();
        
        professionalProfileRepository.save(perfilPlomero);
        log.info("✅ Creado profesional: {}", plomero.getNombreCompleto());

        // Profesional 3: Pintor
        User pintor = User.builder()
            .email("roberto.silva@chaskipro.cl")
            .password(passwordEncoder.encode("password123"))
            .nombreCompleto("Roberto Silva Campos")
            .rut("18901234-5")
            .rol(Rol.PROFESIONAL)
            .activo(true)
            .build();
        
        pintor = userRepository.save(pintor);
        
        ProfessionalProfile perfilPintor = ProfessionalProfile.builder()
            .user(pintor)
            .biografia("Pintor profesional especializado en interiores y exteriores. Trabajo con pinturas ecológicas de alta calidad. Presupuesto sin compromiso.")
            .telefono("+56934567890")
            .categoria(ProfessionCategory.PINTOR)
            .estadoValidacion(EstadoValidacion.APROBADO)
            .promedioCalificacion(new BigDecimal("4.7"))
            .totalCalificaciones(89)
            .serviciosCompletados(95)
            .coberturas(new HashSet<>(comunas.subList(0, 4)))
            .build();
        
        professionalProfileRepository.save(perfilPintor);
        log.info("✅ Creado profesional: {}", pintor.getNombreCompleto());

        // Profesional 4: Cerrajero
        User cerrajero = User.builder()
            .email("luis.morales@chaskipro.cl")
            .password(passwordEncoder.encode("password123"))
            .nombreCompleto("Luis Morales Vega")
            .rut("19012345-6")
            .rol(Rol.PROFESIONAL)
            .activo(true)
            .build();
        
        cerrajero = userRepository.save(cerrajero);
        
        ProfessionalProfile perfilCerrajero = ProfessionalProfile.builder()
            .user(cerrajero)
            .biografia("Cerrajero de emergencia disponible 24 horas. Apertura de puertas, cambio de cerraduras, duplicado de llaves. Servicio rápido y garantizado.")
            .telefono("+56945678901")
            .categoria(ProfessionCategory.CERRAJERO)
            .estadoValidacion(EstadoValidacion.APROBADO)
            .promedioCalificacion(new BigDecimal("4.6"))
            .totalCalificaciones(156)
            .serviciosCompletados(167)
            .coberturas(new HashSet<>(comunas.subList(2, 8)))
            .build();
        
        professionalProfileRepository.save(perfilCerrajero);
        log.info("✅ Creado profesional: {}", cerrajero.getNombreCompleto());

        // Profesional 5: Técnico de Refrigeración
        User tecnicoRefri = User.builder()
            .email("andres.gonzalez@chaskipro.cl")
            .password(passwordEncoder.encode("password123"))
            .nombreCompleto("Andrés González Ramírez")
            .rut("20123456-7")
            .rol(Rol.PROFESIONAL)
            .activo(true)
            .build();
        
        tecnicoRefri = userRepository.save(tecnicoRefri);
        
        ProfessionalProfile perfilTecnico = ProfessionalProfile.builder()
            .user(tecnicoRefri)
            .biografia("Técnico especializado en aire acondicionado y refrigeración. Instalación, mantención y reparación de equipos split y centrales. Certificado SEC.")
            .telefono("+56956789012")
            .categoria(ProfessionCategory.TECNICO_REFRIGERACION)
            .estadoValidacion(EstadoValidacion.APROBADO)
            .promedioCalificacion(new BigDecimal("4.9"))
            .totalCalificaciones(142)
            .serviciosCompletados(158)
            .coberturas(new HashSet<>(comunas.subList(0, 6)))
            .build();
        
        professionalProfileRepository.save(perfilTecnico);
        log.info("✅ Creado profesional: {}", tecnicoRefri.getNombreCompleto());

        // Profesional 6: Carpintero
        User carpintero = User.builder()
            .email("pedro.ramirez@chaskipro.cl")
            .password(passwordEncoder.encode("password123"))
            .nombreCompleto("Pedro Ramírez Castro")
            .rut("21234567-8")
            .rol(Rol.PROFESIONAL)
            .activo(true)
            .build();
        
        carpintero = userRepository.save(carpintero);
        
        ProfessionalProfile perfilCarpintero = ProfessionalProfile.builder()
            .user(carpintero)
            .biografia("Maestro carpintero con 20 años de experiencia. Muebles a medida, reparaciones, closets, puertas. Trabajo garantizado con materiales de primera calidad.")
            .telefono("+56967890123")
            .categoria(ProfessionCategory.CARPINTERO)
            .estadoValidacion(EstadoValidacion.APROBADO)
            .promedioCalificacion(new BigDecimal("4.8"))
            .totalCalificaciones(98)
            .serviciosCompletados(104)
            .coberturas(new HashSet<>(comunas.subList(1, 5)))
            .build();
        
        professionalProfileRepository.save(perfilCarpintero);
        log.info("✅ Creado profesional: {}", carpintero.getNombreCompleto());

        // Profesional 7: Jardinero
        User jardinero = User.builder()
            .email("juan.lopez@chaskipro.cl")
            .password(passwordEncoder.encode("password123"))
            .nombreCompleto("Juan López Fernández")
            .rut("22345678-9")
            .rol(Rol.PROFESIONAL)
            .activo(true)
            .build();
        
        jardinero = userRepository.save(jardinero);
        
        ProfessionalProfile perfilJardinero = ProfessionalProfile.builder()
            .user(jardinero)
            .biografia("Jardinero profesional. Mantención de jardines, poda de árboles, diseño paisajístico, sistemas de riego. Servicio mensual disponible.")
            .telefono("+56978901234")
            .categoria(ProfessionCategory.JARDINERO)
            .estadoValidacion(EstadoValidacion.APROBADO)
            .promedioCalificacion(new BigDecimal("4.7"))
            .totalCalificaciones(76)
            .serviciosCompletados(82)
            .coberturas(new HashSet<>(comunas.subList(2, 7)))
            .build();
        
        professionalProfileRepository.save(perfilJardinero);
        log.info("✅ Creado profesional: {}", jardinero.getNombreCompleto());

        // Profesional 8: Técnico de Computadores
        User tecnicoPC = User.builder()
            .email("francisco.herrera@chaskipro.cl")
            .password(passwordEncoder.encode("password123"))
            .nombreCompleto("Francisco Herrera Díaz")
            .rut("23456789-0")
            .rol(Rol.PROFESIONAL)
            .activo(true)
            .build();
        
        tecnicoPC = userRepository.save(tecnicoPC);
        
        ProfessionalProfile perfilTecnicoPC = ProfessionalProfile.builder()
            .user(tecnicoPC)
            .biografia("Técnico en computación e informática. Reparación de PC y notebooks, instalación de redes, formateo, respaldo de datos. Soporte técnico remoto disponible.")
            .telefono("+56989012345")
            .categoria(ProfessionCategory.TECNICO_COMPUTACION)
            .estadoValidacion(EstadoValidacion.APROBADO)
            .promedioCalificacion(new BigDecimal("4.8"))
            .totalCalificaciones(134)
            .serviciosCompletados(145)
            .coberturas(new HashSet<>(comunas.subList(0, 8)))
            .build();
        
        professionalProfileRepository.save(perfilTecnicoPC);
        log.info("✅ Creado profesional: {}", tecnicoPC.getNombreCompleto());
    }

    private void crearCliente() {
        User cliente = User.builder()
            .email("cliente@chaskipro.cl")
            .password(passwordEncoder.encode("password123"))
            .nombreCompleto("María González Pérez")
            .rut("15678901-2")
            .rol(Rol.CLIENTE)
            .activo(true)
            .build();
        
        userRepository.save(cliente);
        log.info("✅ Creado cliente de prueba: {}", cliente.getNombreCompleto());
    }
}
