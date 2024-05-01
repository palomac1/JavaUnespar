#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define ITERATIONS 1000  // número de execuções para obter uma média

// Função para medir o tempo de alocação de memória
double measure_memory_allocation(size_t size) {
    clock_t start = clock();
    void *block = malloc(size);  // Alocar memória
    clock_t end = clock();
    
    if (block != NULL) {
        free(block);  // Liberar memória após a medição
    } else {
        printf("Erro: Não foi possível alocar memória.\n");
    }
    
    return ((double)(end - start)) / CLOCKS_PER_SEC;
}

// Função para calcular o tempo médio de várias execuções
double average_execution_time(double *times, int iterations) {
    double total = 0.0;
    for (int i = 0; i < iterations; i++) {
        total += times[i];
    }
    return total / iterations;
}

int main() {
    // Tamanhos de alocação para teste
    size_t sizes[] = {1024, 8192, 65536, 524288, 4194304};  // 1 KB, 8 KB, 64 KB, 512 KB, 4 MB
    int num_sizes = sizeof(sizes) / sizeof(sizes[0]);

    // Para cada tamanho, medir o tempo de alocação e obter a média
    for (int i = 0; i < num_sizes; i++) {
        double times[ITERATIONS];
        size_t size = sizes[i];
        
        // Medir o tempo para múltiplas execuções
        for (int j = 0; j < ITERATIONS; j++) {
            times[j] = measure_memory_allocation(size);
        }

        // Calcular o tempo médio de alocação
        double avg_time = average_execution_time(times, ITERATIONS);
        printf("Tempo médio para alocação de %zu bytes: %f segundos\n", size, avg_time);
    }

    return 0;
}
