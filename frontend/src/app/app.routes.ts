import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { AuthGuard } from './auth/auth.guard';
import { LayoutComponent } from './layout/layout.component';
import { DenunciasComponent } from './pages/denuncias/denuncias.component';
import { ListaDenunciasComponent } from './pages/denuncias/lista-denuncias/lista-denuncias.component';
import { DetalleDenunciaComponent } from './pages/denuncias/detalle-denuncia/detalle-denuncia.component';
import { CrearDenunciaComponent } from './pages/denuncias/crear-denuncia/crear-denuncia.component';
import { SolicitudesComponent } from './pages/solicitudes/solicitudes.component';
import { NotificacionesComponent } from './pages/notificaciones/notificaciones.component';
import { ListaSolicitudesComponent } from './pages/solicitudes/lista-solicitudes/lista-solicitudes.component';
import { DetalleSolicitudComponent } from './pages/solicitudes/detalle-solicitud/detalle-solicitud.component';
import { CrearSolicitudComponent } from './pages/solicitudes/crear-solicitud/crear-solicitud.component';
import { AuthNoUserGuard } from './auth/auth-no-user.guard';
import { AdminGuard } from './auth/admin.guard';
import { UsuariosComponent } from './pages/usuarios/usuarios.component';
import { DetalleUsuarioComponent } from './pages/usuarios/detalle-usuario/detalle-usuario.component';
import { ListaUsuariosComponent } from './pages/usuarios/lista-usuarios/lista-usuarios.component';
import { RegistroComponent } from './pages/registro/registro.component';

export const routes: Routes = [
    {path: '', redirectTo: '/login', pathMatch: 'full'},
    {path: 'registro', loadComponent: () => import('./pages/registro/registro.component').then(m => m.RegistroComponent)},
    {path: 'login', component: LoginComponent},
    {path: 'app', component: LayoutComponent, canActivate: [AuthGuard],
        children: [
            {path: '', loadComponent: () => import('./pages/home/home.component').then(m => m.HomeComponent)},
            {path: 'denuncias', component: DenunciasComponent, canActivate: [AuthGuard],
                children: [
                    {path: 'crear', component: CrearDenunciaComponent},
                    {path: 'detalle/:id', component: DetalleDenunciaComponent},
                    {path: 'detalle/**', component: ListaDenunciasComponent},
                    {path: 'editar/:id', component: CrearDenunciaComponent},
                    {path: 'editar/**', component: ListaDenunciasComponent},
                    {path: ':tipo', component: ListaDenunciasComponent},
                    {path: '', component: ListaDenunciasComponent}
                ]
            },
            {path: 'usuarios', component: UsuariosComponent, canActivate: [AdminGuard],
                children: [
                    {path: 'nuevo', component: RegistroComponent},
                    {path: ':tipo', component: ListaUsuariosComponent},
                    {path: '', component: ListaUsuariosComponent}
                ]
            },
            {path: 'usuario/:id', component: DetalleUsuarioComponent},
            {path: 'usuario/**', redirectTo: ''},
            {path: 'solicitudes', component: SolicitudesComponent, canActivate: [AuthNoUserGuard],
                children: [
                    {path: 'crear', component: CrearSolicitudComponent},
                    {path: ':tipo', component: ListaSolicitudesComponent},
                    {path: '', component: ListaSolicitudesComponent}
                ]
            },
            {path: 'solicitud/:id', component: DetalleSolicitudComponent},
            {path: 'solicitud/**', component: DetalleSolicitudComponent},
            {path: 'notificaciones', component: NotificacionesComponent, canActivate: [AuthGuard]},
            {path: '**', redirectTo: '', pathMatch: 'full'}
        ]
    },
];                     
