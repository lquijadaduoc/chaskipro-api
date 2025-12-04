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

    private void crearProfesionalesMasivos(List<Comuna> comunas) {
        List<ProfesionalSeed> seeds = List.of(
            new ProfesionalSeed("valentina.rios@chaskipro.cl", "Valentina Rios", ProfessionCategory.ELECTRICISTA),
            new ProfesionalSeed("daniel.mella@chaskipro.cl", "Daniel Mella", ProfessionCategory.GASFITER),
            new ProfesionalSeed("fernanda.palma@chaskipro.cl", "Fernanda Palma", ProfessionCategory.PINTOR),
            new ProfesionalSeed("sebastian.mora@chaskipro.cl", "Sebastian Mora", ProfessionCategory.CARPINTERO),
            new ProfesionalSeed("carolina.pavez@chaskipro.cl", "Carolina Pavez", ProfessionCategory.JARDINERO),
            new ProfesionalSeed("ignacio.tapia@chaskipro.cl", "Ignacio Tapia", ProfessionCategory.CERRAJERO),
            new ProfesionalSeed("paula.araya@chaskipro.cl", "Paula Araya", ProfessionCategory.TECNICO_REFRIGERACION),
            new ProfesionalSeed("martin.bravo@chaskipro.cl", "Martin Bravo", ProfessionCategory.TECNICO_COMPUTACION),
            new ProfesionalSeed("sofia.cuevas@chaskipro.cl", "Sofia Cuevas", ProfessionCategory.ELECTRICISTA),
            new ProfesionalSeed("jorge.sanhueza@chaskipro.cl", "Jorge Sanhueza", ProfessionCategory.GASFITER),
            new ProfesionalSeed("macarena.ojeda@chaskipro.cl", "Macarena Ojeda", ProfessionCategory.PINTOR),
            new ProfesionalSeed("felipe.oro@chaskipro.cl", "Felipe Oro", ProfessionCategory.CARPINTERO),
            new ProfesionalSeed("catalina.poblete@chaskipro.cl", "Catalina Poblete", ProfessionCategory.JARDINERO),
            new ProfesionalSeed("rodrigo.munita@chaskipro.cl", "Rodrigo Munita", ProfessionCategory.CERRAJERO),
            new ProfesionalSeed("andrea.guzman@chaskipro.cl", "Andrea Guzman", ProfessionCategory.TECNICO_REFRIGERACION),
            new ProfesionalSeed("francisco.lagos@chaskipro.cl", "Francisco Lagos", ProfessionCategory.TECNICO_COMPUTACION),
            new ProfesionalSeed("patricia.arancibia@chaskipro.cl", "Patricia Arancibia", ProfessionCategory.ELECTRICISTA),
            new ProfesionalSeed("cristian.saez@chaskipro.cl", "Cristian Saez", ProfessionCategory.GASFITER),
            new ProfesionalSeed("barbara.cortez@chaskipro.cl", "Barbara Cortez", ProfessionCategory.PINTOR),
            new ProfesionalSeed("camilo.pizarro@chaskipro.cl", "Camilo Pizarro", ProfessionCategory.CARPINTERO),
            new ProfesionalSeed("monica.verdugo@chaskipro.cl", "Monica Verdugo", ProfessionCategory.JARDINERO),
            new ProfesionalSeed("ricardo.vargas@chaskipro.cl", "Ricardo Vargas", ProfessionCategory.CERRAJERO),
            new ProfesionalSeed("javiera.torrealba@chaskipro.cl", "Javiera Torrealba", ProfessionCategory.TECNICO_REFRIGERACION),
            new ProfesionalSeed("tomas.vergara@chaskipro.cl", "Tomas Vergara", ProfessionCategory.TECNICO_COMPUTACION),
            new ProfesionalSeed("lorena.yanez@chaskipro.cl", "Lorena Yanez", ProfessionCategory.ELECTRICISTA),
            new ProfesionalSeed("benjamin.cortes@chaskipro.cl", "Benjamin Cortes", ProfessionCategory.GASFITER),
            new ProfesionalSeed("natalia.bustos@chaskipro.cl", "Natalia Bustos", ProfessionCategory.PINTOR),
            new ProfesionalSeed("vicente.fuentes@chaskipro.cl", "Vicente Fuentes", ProfessionCategory.CARPINTERO),
            new ProfesionalSeed("maria.poblete@chaskipro.cl", "Maria Poblete", ProfessionCategory.JARDINERO),
            new ProfesionalSeed("felix.rivera@chaskipro.cl", "Felix Rivera", ProfessionCategory.CERRAJERO),
            new ProfesionalSeed("claudia.espinoza@chaskipro.cl", "Claudia Espinoza", ProfessionCategory.TECNICO_REFRIGERACION),
            new ProfesionalSeed("gustavo.vidal@chaskipro.cl", "Gustavo Vidal", ProfessionCategory.TECNICO_COMPUTACION),
            new ProfesionalSeed("pamela.galvez@chaskipro.cl", "Pamela Galvez", ProfessionCategory.ELECTRICISTA),
            new ProfesionalSeed("mauricio.valdes@chaskipro.cl", "Mauricio Valdes", ProfessionCategory.GASFITER),
            new ProfesionalSeed("daniela.acevedo@chaskipro.cl", "Daniela Acevedo", ProfessionCategory.PINTOR),
            new ProfesionalSeed("hector.quiroz@chaskipro.cl", "Hector Quiroz", ProfessionCategory.CARPINTERO),
            new ProfesionalSeed("karen.tapia@chaskipro.cl", "Karen Tapia", ProfessionCategory.JARDINERO),
            new ProfesionalSeed("alonso.salazar@chaskipro.cl", "Alonso Salazar", ProfessionCategory.CERRAJERO),
            new ProfesionalSeed("veronica.arenas@chaskipro.cl", "Veronica Arenas", ProfessionCategory.TECNICO_REFRIGERACION),
            new ProfesionalSeed("simon.arancibia@chaskipro.cl", "Simon Arancibia", ProfessionCategory.TECNICO_COMPUTACION),
            new ProfesionalSeed("gloria.carrasco@chaskipro.cl", "Gloria Carrasco", ProfessionCategory.ELECTRICISTA),
            new ProfesionalSeed("pablo.soto@chaskipro.cl", "Pablo Soto", ProfessionCategory.GASFITER),
            new ProfesionalSeed("alejandra.cisternas@chaskipro.cl", "Alejandra Cisternas", ProfessionCategory.PINTOR),
            new ProfesionalSeed("leonardo.iglesias@chaskipro.cl", "Leonardo Iglesias", ProfessionCategory.CARPINTERO)
        );

        for (int i = 0; i < seeds.size(); i++) {
            ProfesionalSeed seed = seeds.get(i);
            User user = User.builder()
                    .email(seed.email())
                    .password(passwordEncoder.encode("password123"))
                    .nombreCompleto(seed.nombre())
                    .rut(String.format("30%07d-%d", i + 1, (i % 9) + 1))
                    .rol(Rol.PROFESIONAL)
                    .activo(true)
                    .build();

            user = userRepository.save(user);

            Set<Comuna> coberturas = obtenerCoberturas(comunas, i % comunas.size(), 4);
            BigDecimal rating = new BigDecimal(String.format("4.%d", (i % 10)));

            ProfessionalProfile profile = ProfessionalProfile.builder()
                    .user(user)
                    .biografia("Profesional con experiencia y vocación de servicio en " + seed.categoria().name().toLowerCase())
                    .telefono(String.format("+5699%08d", i + 10000000))
                    .categoria(seed.categoria())
                    .estadoValidacion(EstadoValidacion.APROBADO)
                    .promedioCalificacion(rating)
                    .totalCalificaciones(60 + i)
                    .serviciosCompletados(80 + (i * 2))
                    .coberturas(coberturas)
                    .build();

            professionalProfileRepository.save(profile);
            log.info("✅ Creado profesional extra: {}", user.getNombreCompleto());
        }
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

    private Set<Comuna> obtenerCoberturas(List<Comuna> comunas, int inicio, int cantidad) {
        Set<Comuna> cobertura = new HashSet<>();
        for (int i = 0; i < cantidad; i++) {
            cobertura.add(comunas.get((inicio + i) % comunas.size()));
        }
        return cobertura;
    }

    private record ProfesionalSeed(String email, String nombre, ProfessionCategory categoria) { }
}
