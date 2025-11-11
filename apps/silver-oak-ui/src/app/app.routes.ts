import { Route } from '@angular/router';
import { CharactersList } from './view/characters/characters-list.component';
import { MainMenu } from './view/main-menu/main-menu';
import { CharactersCreation } from './view/characters-creation/characters-creation';
import { Arena } from './view/arena/arena';
import { Login } from './core/component/login/login';
import { Signin } from './core/component/signin/signin';
import { AuthGuard } from './core/guards/auth-guard';

export const appRoutes: Route[] = [
  {path: '', component: MainMenu, canActivate: [AuthGuard]},
  {path: 'characters', component: CharactersList, canActivate: [AuthGuard]},
  {path: 'character-creation', component: CharactersCreation, canActivate: [AuthGuard]},
  {path: 'arena/:characterId', component: Arena, canActivate: [AuthGuard]},
  {path: 'login', component: Login},
  {path: 'signin', component: Signin}
];
