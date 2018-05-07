import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { LoaderComponent } from './loader/loader.component';
import { Nl2brPipe } from './pipes/nl2br.pipe';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    LoaderComponent, Nl2brPipe
  ],
  exports: [
    LoaderComponent, Nl2brPipe
  ]
})
export class SharedModule { }
