import { DataChartService } from './../../services/data-chart.service';
import Chart from 'chart.js/auto';
import { ChartModule } from 'primeng/chart';
import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';
import { response } from 'express';
import { error } from 'console';
@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ChartModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit{
        title = 'Dashboard';
        public chart: any;
    
        data: any;
        data2: any;
        options2: any;
        data3: any;
        options3: any;
        totalEntrees: number = 0; // Initialisation de la propriété
        totalSorties: number = 0; // Initialisation de la propriété
        salesByCategory: any[] = []; // Initialisation de la propriété 
    
        constructor(private dataChartService: DataChartService) { }
    
        ngOnInit() {
            this.getTrends();
            this.getStats();
            this.getSalesByCategory();
        }
    
        getTrends() {
            this.dataChartService.getTrends().subscribe(
                response => {
                    this.data = response;
                    this.createChart();
                },
                error => {
                    console.error('Erreur lors de la récupération des tendances', error);
                }
            );
        }
    
        getStats() {
            this.dataChartService.getStats().subscribe(
                response => {
                    this.totalEntrees = response.totalEntrees;
                    this.totalSorties = response.totalSorties;
                },
                error => {
                    console.error('Erreur lors de la récupération des statistiques', error);
                }
            );
        }
    
        getSalesByCategory() {
            this.dataChartService.getSalesByCategory().subscribe(
                response => {
                    this.salesByCategory = response;
                    this.createSalesByCategoryChart();
                },
                error => {
                    console.error('Erreur lors de la récupération des ventes par catégories', error);
                }
            );
        }
    
        createChart() {
            // Votre logique pour initialiser le graphique avec this.data
            const documentStyle = getComputedStyle(document.documentElement);
            const textColor = documentStyle.getPropertyValue('--text-color');
            const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
            const surfaceBorder = documentStyle.getPropertyValue('--surface-border');
    
            this.data = {
                labels: this.data.entrees.map((e: [number, number]) => `Month ${e[0]}`),
                datasets: [
                    {
                        label: 'Entrées',
                        borderColor: documentStyle.getPropertyValue('--blue-500'),
                        backgroundColor: documentStyle.getPropertyValue('--blue-500'),
                        data: this.data.entrees.map((e: [number, number]) => e[1])
                    },
                    {
                        label: 'Sorties',
                        borderColor: documentStyle.getPropertyValue('--pink-500'),
                        backgroundColor: documentStyle.getPropertyValue('--pink-500'),
                        data: this.data.sorties.map((s: [number, number]) => s[1])
                    }
                ]
            };
    
            // Initialiser le chart ici si nécessaire
        }
    
        Chart2() {
            const documentStyle = getComputedStyle(document.documentElement);
            const textColor = documentStyle.getPropertyValue('--text-color');
            const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
            const surfaceBorder = documentStyle.getPropertyValue('--surface-border');
    
            this.data2 = {
                labels: ['Janvier', 'Fevrier', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet'],
                datasets: [
                    {
                        label: 'Bons Entrées',
                        backgroundColor: documentStyle.getPropertyValue('--blue-500'),
                        borderColor: documentStyle.getPropertyValue('--blue-500'),
                        data: [65, 59, 80, 81, 56, 55, 40]
                    },
                    {
                        label: 'Bons Sorties',
                        backgroundColor: documentStyle.getPropertyValue('--pink-500'),
                        borderColor: documentStyle.getPropertyValue('--pink-500'),
                        data: [28, 48, 40, 19, 86, 27, 90]
                    }
                ]
            };
    
            this.options2 = {
                maintainAspectRatio: false,
                aspectRatio: 0.8,
                plugins: {
                    legend: {
                        labels: {
                            color: textColor
                        }
                    }
                },
                scales: {
                    x: {
                        ticks: {
                            color: textColorSecondary,
                            font: {
                                weight: 500
                            }
                        },
                        grid: {
                            color: surfaceBorder,
                            drawBorder: false
                        }
                    },
                    y: {
                        ticks: {
                            color: textColorSecondary
                        },
                        grid: {
                            color: surfaceBorder,
                            drawBorder: false
                        }
                    }
                }
            };
        }
    
        Chart3() {
            const documentStyle = getComputedStyle(document.documentElement);
            const textColor = documentStyle.getPropertyValue('--text-color');
            const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
            const surfaceBorder = documentStyle.getPropertyValue('--surface-border');
    
            this.data3 = {
                labels: ['Janvier', 'Fevrier', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet'],
                datasets: [
                    {
                        label: 'Bons Entrées',
                        backgroundColor: documentStyle.getPropertyValue('--blue-500'),
                        borderColor: documentStyle.getPropertyValue('--blue-500'),
                        data: [65, 59, 80, 81, 56, 55, 40]
                    },
                    {
                        label: 'Bons Sorties',
                        backgroundColor: documentStyle.getPropertyValue('--pink-500'),
                        borderColor: documentStyle.getPropertyValue('--pink-500'),
                        data: [28, 48, 40, 19, 86, 27, 90]
                    }
                ]
            };
    
            this.options3 = {
                maintainAspectRatio: false,
                aspectRatio: 0.8,
                plugins: {
                    legend: {
                        labels: {
                            color: textColor
                        }
                    }
                },
                scales: {
                    x: {
                        ticks: {
                            color: textColorSecondary,
                            font: {
                                weight: 500
                            }
                        },
                        grid: {
                            color: surfaceBorder,
                            drawBorder: false
                        }
                    },
                    y: {
                        ticks: {
                            color: textColorSecondary
                        },
                        grid: {
                            color: surfaceBorder,
                            drawBorder: false
                        }
                    }
                }
            };
        }
    
        createSalesByCategoryChart() {
            const documentStyle = getComputedStyle(document.documentElement);
            const textColor = documentStyle.getPropertyValue('--text-color');
            const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
            const surfaceBorder = documentStyle.getPropertyValue('--surface-border');
    
            this.data3 = {
                labels: this.salesByCategory.map(category => category[0]),
                datasets: [
                    {
                        label: 'Ventes par Catégories',
                        backgroundColor: documentStyle.getPropertyValue('--blue-500'),
                        borderColor: documentStyle.getPropertyValue('--blue-500'),
                        data: this.salesByCategory.map(category => category[1])
                    }
                ]
            };
    
            this.options3 = {
                maintainAspectRatio: false,
                aspectRatio: 0.8,
                plugins: {
                    legend: {
                        labels: {
                            color: textColor
                        }
                    }
                },
                scales: {
                    x: {
                        ticks: {
                            color: textColorSecondary,
                            font: {
                                weight: 500
                            }
                        },
                        grid: {
                            color: surfaceBorder,
                            drawBorder: false
                        }
                    },
                    y: {
                        ticks: {
                            color: textColorSecondary
                        },
                        grid: {
                            color: surfaceBorder,
                            drawBorder: false
                        }
                    }
                }
            };
        }
}
    