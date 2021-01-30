import { Routes } from '@angular/router';

import { HomeComponent } from '../../home/home.component';
import { UserComponent } from '../../user/user.component';
import { EventsComponent } from '../../events/events.component';
import { BulletinComponent } from '../../bulletin/bulletin.component';
import { ClubsComponent } from '../../clubs/clubs.component';
import { AdvancedAuthGuard } from 'app/shared/services/authentication/advancedAuthGuard';

export const AdminLayoutRoutes: Routes = [
    { path: 'home',      component: HomeComponent },
    { path: 'user',           component: UserComponent },
    { path: 'events',          component: EventsComponent, canActivate: [AdvancedAuthGuard] },
    { path: 'bulletins',     component: BulletinComponent, canActivate: [AdvancedAuthGuard] },
    { path: 'clubs',          component: ClubsComponent }
];
